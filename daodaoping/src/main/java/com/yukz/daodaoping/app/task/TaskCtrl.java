package com.yukz.daodaoping.app.task;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
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
import com.yukz.daodaoping.app.common.AssisantTypeEnum;
import com.yukz.daodaoping.app.enums.IsAllowEnum;
import com.yukz.daodaoping.app.task.enums.TaskStatusEnum;
import com.yukz.daodaoping.app.task.request.TaskApplyRequest;
import com.yukz.daodaoping.common.exception.BDException;
import com.yukz.daodaoping.common.utils.R;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;
import com.yukz.daodaoping.task.domain.TaskTypeInfoDO;
import com.yukz.daodaoping.task.enums.PlatformEnum;
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
	private TaskApplyInfoService taskApplyInfoDOService;
	
	

	/**
	 * 助力金额计算
	 * @return
	 */
	@GetMapping("price/{taskId}/{num}")
	public R taskAmoutCalculate(@PathVariable("taskId") Long taskId, @PathVariable("num") int num) {
		 TaskTypeInfoDO taskTypeInfo = taskTypeInfoService.get(taskId);
		 Integer price = taskTypeInfo.getPrice();
		 BigDecimal priceDecimal = new BigDecimal(price);
		 BigDecimal amountDecimal = priceDecimal.multiply(new BigDecimal(num));
		 Map<String,Integer> result = new HashMap<String, Integer>();
		 result.put("price",amountDecimal.intValue());
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
		boolean flag = checkInternalLimited(taskAppleRequest);
		if (!flag) {
			return R.error("任务开始时间与当前时间的间隔必须大于10分钟");
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
		return R.ok();
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
		if(TaskStatusEnum.getEnumByStatus(taskStatus) == TaskStatusEnum.CANCEL) {
			return R.ok();
		}else if (TaskStatusEnum.getEnumByStatus(taskStatus) == TaskStatusEnum.PENDING
				|| TaskStatusEnum.getEnumByStatus(taskStatus) == TaskStatusEnum.END) {
			return R.error("当前任务无法取消");
		}else {
			taskExecuteBiz.cancel(taskApplyInfoDO);
		}
		return R.ok();
	}

	/**
	 * 任务提交：生成订单并更新任务状态，并计算任务过期时间
	 * 
	 * @param tasksubmit
	 * @return
	 */
	@PostMapping("submit/{id}")
	public R taskSubmit(@PathVariable("id") Long taskApplyInfoId,UserAgent userAgent) {
		TaskApplyInfoDO taskApplyInfo = taskApplyInfoDOService.get(taskApplyInfoId);
		// 幂等性检查
		if (TaskStatusEnum.getEnumByStatus(taskApplyInfo.getTaskStatus()) == TaskStatusEnum.WAIT) {
			return R.ok();
		}
		try {
			// TODO 调用支付接口
			// 调用成功后，更新任务状态
			taskApplyInfo.setTaskStatus(TaskStatusEnum.WAIT.getStatus());
			taskApplyInfoDOService.update(taskApplyInfo);
		} catch (BDException ex) {
			return R.error("支付失败,原因:" + ex.getMessage());
		}
		return R.ok();
	}

	@GetMapping("taskType/{typeId}/{pageNum}/{pageSize}")
	public PageInfo<TaskApplyInfoDO> getTaskApplyInfoDOListByTaskType(UserAgent userAgent ,
			@PathVariable("typeId") Long typeId, @PathVariable("pageNum") int pageNum,
			@PathVariable("pageSize") int pageSize) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("user_id", userAgent.getUserId());
		paramMap.put("agent_id", userAgent.getAgentId());
		paramMap.put("task_type_id", typeId);
		List<TaskApplyInfoDO> list = taskApplyInfoDOService.list(paramMap);
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<TaskApplyInfoDO> pageResult = new PageInfo<TaskApplyInfoDO>(list);
		return pageResult; 
	}
	
	@GetMapping("taskStatus/{taskStatus}/{pageNum}/{pageSize}")
	public PageInfo<TaskApplyInfoDO> getTaskApplyInfoDOLisByTaskStatus(UserAgent userAgent,
			@PathVariable("taskStatus") String taskStatus, @PathVariable("pageNum") int pageNum,
			@PathVariable("pageSize") int pageSize) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("user_id", userAgent.getUserId());
		paramMap.put("agent_id", userAgent.getAgentId());
		paramMap.put("task_status", taskStatus);
		List<TaskApplyInfoDO> list = taskApplyInfoDOService.list(paramMap);
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<TaskApplyInfoDO> pageResult = new PageInfo<TaskApplyInfoDO>(list);
		return pageResult; 
	}
	
	
	
	

	/**
	 * 判断任务执行时间是否符合系统要求 if(isAppointment) {提交的预约时间必须晚于当前时间10分钟} else {立即开始}
	 * 
	 * @param taskAppleRequest
	 * @return
	 */
	public boolean checkInternalLimited(TaskApplyRequest taskAppleRequest) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		if (!taskAppleRequest.isAppointment()) {
			taskAppleRequest.setStartTime(cal.getTime());
			return true;
		} else {
			cal.add(LIMITED_INTERVAL, Calendar.MINUTE);
			Calendar taskStartCal = Calendar.getInstance();
			taskStartCal.setTime(taskAppleRequest.getStartTime());
			if (!taskStartCal.before(cal)) { // 开始时间不满足时间间隔的，不允许初始化
				return false;
			}
			taskAppleRequest.setStartTime(taskStartCal.getTime());
			cal.add(LIMITED_INTERVAL, Calendar.MINUTE);
			taskAppleRequest.setExpireTime(taskStartCal.getTime());
		}
		return true;
	}

}
