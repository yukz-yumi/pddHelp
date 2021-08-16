package com.yukz.daodaoping.app.task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.yukz.daodaoping.app.auth.vo.UserAgent;
import com.yukz.daodaoping.app.task.enums.TaskAcceptionStatusEunm;
import com.yukz.daodaoping.app.task.enums.TaskIsPaidEnum;
import com.yukz.daodaoping.app.task.enums.TaskStatusEnum;
import com.yukz.daodaoping.app.task.request.TaskRequest;
import com.yukz.daodaoping.app.task.threads.SetTaskExecutionThread;
import com.yukz.daodaoping.common.amqp.AmqpHandler;
import com.yukz.daodaoping.common.amqp.MqConstants;
import com.yukz.daodaoping.common.exception.BDException;
import com.yukz.daodaoping.order.domain.OrderInfoDO;
import com.yukz.daodaoping.order.service.OrderInfoService;
import com.yukz.daodaoping.system.config.RedisHandler;
import com.yukz.daodaoping.task.domain.TaskAcceptInfoDO;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;
import com.yukz.daodaoping.task.service.TaskAcceptInfoService;
import com.yukz.daodaoping.task.service.TaskApplyInfoService;

/**
 * 发单/接单 业务处理类
 * @author micezhao
 *
 */
@Service
public class TaskExecuteBiz {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskExecuteBiz.class);
	
	// 申请任务 时间-> 决定任务是否立即开始
	// 计算配置的任务过期时间
	// 定时任务扫描
	// 接受延迟任务的处理
	// 取消任务：是否申请退款
	
//	private static final int TASK_DELAY_MIN = 2;
	
	@Autowired
	private TaskApplyInfoService taskApplyInfoService;
	
	@Autowired
	private TaskAcceptInfoService taskAcceptInfoService;
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired
	private RedissonClient redissonClient;
	
	@Autowired
	private RedisHandler redisHandler;
	
	@Autowired
	private OrderInfoService orderInfo;
	
	@Autowired
	private AmqpHandler mqHandler;
	
	
	@Value("${ttl.completed}")
	private int interval;
	
	@Value("${proportion.task}")
	private int proportionTask;
	
	@Value("${proportion.commission.level1}")
	private int commissionLevel1;
	
	@Value("${proportion.commission.level2}")
	private int commissionLevel2;
	
	
	@Value("${ttl.task}")
	private int ttl;
	
	/**
	 * 初始化任务记录
	 * @return
	 */
	public boolean initTaskApplyInfo(TaskApplyInfoDO taskApplyInfoDO) {
		taskApplyInfoDO.setGmtCreate(new Date());
		if(taskApplyInfoDO.getStartTime() != null ) {
			Date taskExpireTime = getTaskExpireTime(taskApplyInfoDO);
			taskApplyInfoDO.setExpireTime(taskExpireTime);			
		}
		int i = taskApplyInfoService.save(taskApplyInfoDO);
		return i >= 1 ? true : false ;
	}
	
		
	public boolean editTaskApplyInfo(TaskApplyInfoDO taskApplyInfoDO ) {
		String taskStatus = taskApplyInfoDO.getTaskStatus();
		Date taskExpireTime = getTaskExpireTime(taskApplyInfoDO);
		int i = 0;
		if(TaskStatusEnum.getEnumByStatus(taskStatus) == TaskStatusEnum.WAIT ) {
			if(isSameDay(taskApplyInfoDO)) {
				taskApplyInfoDO.setExpireTime(taskExpireTime);
				i = taskApplyInfoService.update(taskApplyInfoDO);
				String threadName = Thread.currentThread().getName();
				List<TaskApplyInfoDO> list = new ArrayList<TaskApplyInfoDO>();
				list.add(taskApplyInfoDO);
				taskExecutor.submit(new SetTaskExecutionThread(threadName,list,redissonClient,mqHandler));
			}
		}else if(TaskStatusEnum.getEnumByStatus(taskStatus) == TaskStatusEnum.SUSPEND) {
			taskApplyInfoDO.setExpireTime(taskExpireTime);
			i = taskApplyInfoService.update(taskApplyInfoDO);
		}else {
			throw new BDException("当前状态不允许修改任务申请");
		}
		return i >= 1 ? true : false ;
	}
	
	
	
	public Date getTaskExpireTime(TaskApplyInfoDO taskApplyInfoDO) {
		if(taskApplyInfoDO.getExpireTime() != null) {
			return taskApplyInfoDO.getExpireTime();
		}
		Date startTime = taskApplyInfoDO.getStartTime();
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		cal.add(Calendar.MILLISECOND, ttl);
		return cal.getTime(); 
	}
	
	
	
	public boolean isSameDay(TaskApplyInfoDO taskApplyInfoDO) {
		Calendar cal_task =Calendar.getInstance() ;
		Calendar cal_now = Calendar.getInstance() ;
		cal_now.setTime(new Date());
		cal_task.setTime(taskApplyInfoDO.getStartTime());
		boolean sameDay = cal_task.get(Calendar.YEAR) == cal_now.get(Calendar.YEAR) && 
				cal_task.get(Calendar.DAY_OF_YEAR) == cal_now.get(Calendar.DAY_OF_YEAR);
		return sameDay;
	}

	/**
	 * 取消任务，如果任务状态是 wait 需要退费
	 * @param taskApplyInfoDO
	 * @return
	 */
	public boolean cancel(TaskApplyInfoDO taskApplyInfoDO) {
		String taskStatus = taskApplyInfoDO.getTaskStatus();
		if(TaskStatusEnum.getEnumByStatus(taskStatus) == TaskStatusEnum.WAIT) {
			taskApplyInfoDO.setTaskStatus(TaskStatusEnum.CANCEL.getStatus());
			// TODO 走退费流程
		}else if(TaskStatusEnum.getEnumByStatus(taskStatus) == TaskStatusEnum.SUSPEND) {
			taskApplyInfoDO.setTaskStatus(TaskStatusEnum.CANCEL.getStatus());
		}
		taskApplyInfoDO.setGmtModify(new Date());
		int i = taskApplyInfoService.update(taskApplyInfoDO);
		return i >= 1 ? true : false ;
	}
	
	/**
	 * 关闭任务，在任务对应的订单过期时执行
	 * @throws Exception 
	 */
	public boolean close(Long taskId) throws Exception {
		TaskApplyInfoDO taskApplyInfoDO = taskApplyInfoService.get(taskId);
		taskApplyInfoDO.setTaskStatus(TaskStatusEnum.CLOSE.getStatus());
		taskApplyInfoDO.setGmtModify(new Date());
		int i = taskApplyInfoService.update(taskApplyInfoDO);
		if(i == 1) {
			logger.info("taskId:{}, 已关闭",taskId);
			return true;
		}else {
			throw new Exception("任务关闭失败");
		}
	}
	
	public TaskAcceptInfoDO takeTask(Long taskId,UserAgent userAgent) throws Exception {
		TaskAcceptInfoDO taskAcceptInfoDO = new TaskAcceptInfoDO();
		TaskApplyInfoDO taskApplyInfo = taskApplyInfoService.get(taskId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("agentId", taskApplyInfo.getAgentId());
		map.put("taskId", taskApplyInfo.getId());
		List<OrderInfoDO> orderList= orderInfo.list(map);
		if(orderList.isEmpty()) {
			throw new Exception("当前任务对应的订单为空~~~");
		}
		OrderInfoDO orderItem = orderList.get(0);
		Long orderAmout = orderItem.getTotalAmount();
		long taskAmount = new BigDecimal(orderAmout).multiply(new BigDecimal(proportionTask/100)).longValue();
		taskAcceptInfoDO.setAgentId(userAgent.getAgentId());
		taskAcceptInfoDO.setUserId(userAgent.getUserId());
		taskAcceptInfoDO.setTaskId(taskId);
		taskAcceptInfoDO.setAmount(taskAmount);
		taskAcceptInfoDO.setTaskStatus(TaskAcceptionStatusEunm.PENDING.getStatus());
		taskAcceptInfoDO.setHasPaid(TaskIsPaidEnum.UNPAID.getStatus());
		taskAcceptInfoDO.setGmtCreate(new Date());
		Calendar cal = Calendar.getInstance();
		cal.setTime(taskAcceptInfoDO.getGmtCreate());
		cal.add(Calendar.MINUTE, interval);
		taskAcceptInfoDO.setExpireTime(cal.getTime());
		taskAcceptInfoService.save(taskAcceptInfoDO);
		// 发送消息延迟队列
		mqHandler.sendDelayMessage(taskAcceptInfoDO.getId(), MqConstants.TASK_TAKE_EXPIRE_ROUTER_KEY);
		return taskAcceptInfoDO;
	}
	
	/**
	 * @deprecated
	 * 开启任务，在订单被确认时调用
	 * @param taskId
	 * @return
	 */
	public boolean open(Long taskId) {
		TaskApplyInfoDO taskApplyInfoDO = taskApplyInfoService.get(taskId);
		taskApplyInfoDO.setTaskStatus(TaskStatusEnum.PENDING.getStatus());
		taskApplyInfoDO.setGmtModify(new Date());
		int i = taskApplyInfoService.update(taskApplyInfoDO);
		return i == 1 ? true : false ;
	}
	
	
	public void repay(Long agentId,Long taskId) {
		String key = "agentId:"+agentId+"task_id:"+taskId;
		RLock rlock = redissonClient.getLock(key);
		try {
			rlock.tryLock(10, TimeUnit.SECONDS);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("agentId", agentId);
			map.put("id", taskId);
			map.put("taskStatus", TaskStatusEnum.PENDING.getStatus());
			List<TaskApplyInfoDO> list = taskApplyInfoService.list(map);
			if(list.isEmpty()) {
				logger.info("无需补偿");
				return ;
			}
			boolean flag = addTaskRemainNum(key,taskId);
			if(flag) {
				logger.info("任务未执行数量补偿成功");
			}else {
				logger.warn("任务未执行数量补偿失败");
			}
		} catch (InterruptedException e) {
			rlock.unlock();
		}
	}
	
	
	public boolean subTaskRemainNum(String key) {
		int remain =  getTaskRemain(key);
		if(remain>0) {			
			redisHandler.hmSet(TaskConstants.MAP_REMAIN, key, remain--);
			logger.info("任务:{} 剩余任务数扣减成功,当前剩余任务数量:{}",remain);
			return true;
		}
		logger.info("任务:{} 剩余任务数扣减失败,当前剩余任务数量:{}",remain);
		return false;
	}
	
	public boolean addTaskRemainNum(String key,Long taskId) {
		TaskApplyInfoDO taskApplyInfo = taskApplyInfoService.get(taskId);
		int  totalTaskNum = taskApplyInfo.getTaskNumber();
		int remain = getTaskRemain(key);
		if(remain == totalTaskNum) {
			logger.error("任务:{} 数量的待执行数量已达到上限",remain);
			return false;
		}
		redisHandler.hmSet(TaskConstants.MAP_REMAIN, key, remain++);
		return true;
	}
	
	public int getTaskRemain(String key) {
		int remain = (int)redisHandler.hmGet(TaskConstants.MAP_REMAIN, key);
		return remain;
	}
	
	public TaskAcceptInfoDO taskTakeConfirm(TaskRequest taskRequst) throws Exception {
		TaskAcceptInfoDO takeInfo = taskAcceptInfoService.get(taskRequst.getId());
		if(!takeInfo.getTaskStatus().equals(TaskAcceptionStatusEunm.PENDING.getStatus())) {
			throw new Exception("当前接单任务状态为:"+takeInfo.getTaskStatus()+"无法继续操作");
		}
		String certificationUrl = taskRequst.getCertificationUrl();
		takeInfo.setCertificationUrl(certificationUrl);
		takeInfo.setTaskStatus(TaskAcceptionStatusEunm.END.getStatus());
		takeInfo.setGmtModify(new Date());
		int i = taskAcceptInfoService.update(takeInfo);
		if(i >=1 ) {
			return takeInfo;
		}else {
			throw new Exception("接单确认失败");
		}
		
		
	}
	
	
}
