package com.yukz.daodaoping.app.task;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yukz.daodaoping.app.auth.vo.UserAgent;
import com.yukz.daodaoping.app.order.OrderEnum;
import com.yukz.daodaoping.app.order.TaskOrderBiz;
import com.yukz.daodaoping.app.task.enums.TaskStatusEnum;
import com.yukz.daodaoping.app.task.request.TaskApplyRequest;
import com.yukz.daodaoping.common.amqp.AmqpHandler;
import com.yukz.daodaoping.common.amqp.MqConstants;
import com.yukz.daodaoping.common.exception.BDException;
import com.yukz.daodaoping.common.utils.R;
import com.yukz.daodaoping.order.domain.OrderInfoDO;
import com.yukz.daodaoping.order.service.OrderInfoService;
import com.yukz.daodaoping.system.config.RedisHandler;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;
import com.yukz.daodaoping.task.domain.TaskTypeInfoDO;
import com.yukz.daodaoping.task.service.TaskApplyInfoService;
import com.yukz.daodaoping.task.service.TaskTypeInfoService;

@RequestMapping("appInt/task/")
@RestController
public class TaskCtrl {

	private static final Logger logger = LoggerFactory.getLogger(TaskCtrl.class);

	public static final int LIMITED_INTERVAL = 10;

	@Autowired
	private TaskTypeInfoService taskTypeInfoService;

	@Autowired
	private TaskExecuteBiz taskExecuteBiz;

	@Autowired
	private TaskOrderBiz taskOrderBiz;

	@Autowired
	private OrderInfoService orderInfoService;

	@Autowired
	private TaskApplyInfoService taskApplyInfoDOService;
	
	@Autowired
	private AmqpHandler amqpHandler;
	
	@Autowired
	private RedissonClient redissonClient;
	
	@Autowired
	private RedisHandler redisHandler;
	
	
	


	/**
	 * 助力金额计算
	 * 
	 * @return
	 */
	@GetMapping("price/{taskId}/{num}")
	public R taskAmoutCalculate(@PathVariable("taskId") Long taskId, @PathVariable("num") int num) {
		TaskTypeInfoDO taskTypeInfo = taskTypeInfoService.get(taskId);
		Integer price = taskTypeInfo.getPrice();
		BigDecimal priceDecimal = new BigDecimal(price);
		BigDecimal amountDecimal = priceDecimal.multiply(new BigDecimal(num));
		Map<String, Integer> result = new HashMap<String, Integer>();
		result.put("price", amountDecimal.intValue());
		return R.ok().put("data", result);
	}

	/**
	 * 保存任务
	 * 
	 * @param taskAppleRequest
	 * @return
	 */
	@PostMapping("save")
	public R taskApply(@RequestBody TaskApplyRequest taskAppleRequest, TaskApplyInfoDO taskApplyInfoDO,
			UserAgent userAgent) {
		if(taskAppleRequest.isAppointment()) {
			boolean flag = checkInternalLimited(taskAppleRequest);
			if (!flag) {
				return R.error("预约任务的开始时间与当前时间的间隔必须大于10分钟");
			}			
		}
		try {
			PropertyUtils.copyProperties(taskApplyInfoDO, taskAppleRequest);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return R.error("对象复制出现异常");
		}
		taskApplyInfoDO.setUserId(userAgent.getUserId());
		taskApplyInfoDO.setAgentId(userAgent.getAgentId());
		taskApplyInfoDO.setTaskStatus(TaskStatusEnum.SUSPEND.getStatus()); // 未支付时的任务为挂起
		taskExecuteBiz.initTaskApplyInfo(taskApplyInfoDO);
		return R.ok().put("data", taskApplyInfoDO);
	}

	@PutMapping("edit/{id}")
	public R taskEdit(@PathVariable("id") Long taskApplyInfoId, @RequestBody TaskApplyRequest taskAppleRequest,
			TaskApplyInfoDO taskApplyInfoDO) {
		taskApplyInfoDO = taskApplyInfoDOService.get(taskApplyInfoId);
		if (taskApplyInfoDO == null) {
			throw new BDException("任务申请记录不存在");
		}
		boolean flag = checkInternalLimited(taskAppleRequest);
		if (!flag) {
			throw new BDException("任务开始时间与当前时间的间隔必须大于10分钟");
		}
		try {
			PropertyUtils.copyProperties(taskApplyInfoDO, taskAppleRequest);
		} catch (Exception e) {
			return R.error("对象复制出现异常");
		}
		taskExecuteBiz.editTaskApplyInfo(taskApplyInfoDO);
		return R.ok();
	}

	@PutMapping("cancel/{id}")
	public R taskcancel(@PathVariable("id") Long taskApplyInfoId) {
		TaskApplyInfoDO taskApplyInfoDO = taskApplyInfoDOService.get(taskApplyInfoId);
		String taskStatus = taskApplyInfoDO.getTaskStatus();
		// 幂等性检查
		if (TaskStatusEnum.getEnumByStatus(taskStatus) == TaskStatusEnum.CANCEL) {
			return R.ok();
		} else if (TaskStatusEnum.getEnumByStatus(taskStatus) == TaskStatusEnum.PENDING
				|| TaskStatusEnum.getEnumByStatus(taskStatus) == TaskStatusEnum.END) {
			return R.error("当前任务无法取消");
		} else {
			taskExecuteBiz.cancel(taskApplyInfoDO);
		}
		return R.ok();
	}

	/**
	 * 任务提交：生成订单->生成支付参数->等待支付结果->用户支付 生成任务执行计划<-更新任务状态<-更新订单状态<-更新支付结果
	 * 
	 * @param tasksubmit
	 * @return
	 */
	@PostMapping("submit/{id}")
	public R taskSubmit(@PathVariable("id") Long taskApplyInfoId, UserAgent userAgent) {
		TaskApplyInfoDO taskApplyInfo = taskApplyInfoDOService.get(taskApplyInfoId);
		if (TaskStatusEnum.getEnumByStatus(taskApplyInfo.getTaskStatus()) != TaskStatusEnum.SUSPEND) {
			return R.error(
					"当前任务状态为:【" + TaskStatusEnum.getEnumByStatus(taskApplyInfo.getTaskStatus()).getDesc() + "】不允许进行提交");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", taskApplyInfoId);
		map.put("orderStatus", OrderEnum.UNPAID.getCode());
		List<OrderInfoDO> orderList = orderInfoService.list(map);
		OrderInfoDO orderInfo = new OrderInfoDO();
		if (!orderList.isEmpty()) {
			orderInfo = orderList.get(0);
		} else {
			orderInfo = taskOrderBiz.initOrder(taskApplyInfo);
			amqpHandler.sendDelayMessage(orderInfo.getId(), MqConstants.ORDER_EXPIRE_ROUTER_KEY);
		}
		Map<String, Object> viewMap = new HashMap<String, Object>();
		viewMap.put("orderInfo", orderInfo);
		viewMap.put("taskApplyInfo", taskApplyInfo);
		return R.ok().put("orderDetail", viewMap);
	}

	@GetMapping("list")
	public R getTaskApplyInfoDOListByTaskType(UserAgent userAgent, HttpServletRequest request) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Long agentId = userAgent.getAgentId();
		String taskType = request.getParameter("type");
		String taskStatus = request.getParameter("status");
		String timeScope = request.getParameter("scope");
		int pageNum = Integer.valueOf(request.getParameter("pageNum"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		if (StringUtils.isNotBlank(taskType)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("taskType", taskType);
			param.put("agentId", agentId);
			TaskTypeInfoDO taskInfo = taskTypeInfoService.list(param).get(0);
			paramMap.put("taskTypeId", taskInfo.getId());
//			paramMap.put("agentId", agentId);
		}
		if (StringUtils.isNotBlank(taskStatus)) {
			paramMap.put("taskStatus", taskStatus);
		}
		Date endDate = new Date();
		if (StringUtils.isNotBlank(timeScope)) {
			endDate = getEndTime(timeScope);
		} else {
			endDate = getEndTime("week");
		}
		paramMap.put("endTime", endDate);
		paramMap.put("userId", userAgent.getUserId());
		paramMap.put("agentId", agentId);
		List<TaskApplyInfoDO> list = taskApplyInfoDOService.list(paramMap);
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<TaskApplyInfoDO> pageResult = new PageInfo<TaskApplyInfoDO>(list);
		return R.ok().put("data", pageResult);
	}
	
	
	@PutMapping("take/{id}")
	public R takeTask(@PathVariable("id") Long taskId,UserAgent userAgent){
		Long agentId = userAgent.getAgentId();
		Long userId = userAgent.getUserId();
		String key = "agentId:"+agentId+"task_id:"+taskId;
		RLock rlock = redissonClient.getLock(key);
		try {
			rlock.tryLock(10, TimeUnit.SECONDS);
			int remainTaskNum = (int)redisHandler.get(key);
			if(remainTaskNum > 0) {
				// 先生成一条接单任务记录
				
				// 在扣减数量，再更新，并将剩余数量同步到redis
				
			}
			
		} catch (InterruptedException e) {
			rlock.unlock();
		}
		return R.ok().put("data", null);
	}
	
	/**
	 * 判断任务执行时间是否符合系统要求 if(isAppointment) {提交的预约时间必须晚于当前时间10分钟} else {立即开始}
	 * 
	 * @param taskAppleRequest
	 * @return
	 */
	private boolean checkInternalLimited(TaskApplyRequest taskAppleRequest) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(LIMITED_INTERVAL, Calendar.MINUTE);
		Calendar taskStartCal = Calendar.getInstance();
		taskStartCal.setTime(taskAppleRequest.getStartTime());
		if (!taskStartCal.before(cal)) { // 开始时间不满足时间间隔的，不允许初始化
			return false;
		}
		taskAppleRequest.setStartTime(taskStartCal.getTime());
		cal.add(LIMITED_INTERVAL, Calendar.MINUTE);
		taskAppleRequest.setExpireTime(taskStartCal.getTime());
		return true;
	}

	public Date getEndTime(String scope) {
		Calendar cal = Calendar.getInstance();
		switch (scope) {
		case "week": // 一周内
			cal.add(Calendar.DAY_OF_MONTH, -7);
			break;
		case "month": // 一个月
			cal.add(Calendar.MONTH, -1);
			break;
		case "quarter":
			cal.add(Calendar.MONTH, -3);
			break;
		case "halfyear":
			cal.add(Calendar.MONTH, -6);
			break;
		default:
			cal.add(Calendar.DAY_OF_MONTH, -7);
			break;
		}
		return cal.getTime();
	}

}
