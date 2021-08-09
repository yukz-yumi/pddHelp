package com.yukz.daodaoping.app.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.yukz.daodaoping.app.task.enums.TaskStatusEnum;
import com.yukz.daodaoping.app.task.threads.SetTaskExecutionThread;
import com.yukz.daodaoping.common.exception.BDException;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;
import com.yukz.daodaoping.task.domain.TaskTypeInfoDO;
import com.yukz.daodaoping.task.service.TaskApplyInfoService;
import com.yukz.daodaoping.task.service.TaskTypeInfoService;

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
	
	
	@Autowired
	private TaskApplyInfoService taskApplyInfoService;
	
	@Autowired
	private TaskTypeInfoService taskTypeInfoService;
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired
	private RedissonClient redissonClient;
	
	@Autowired
	private RabbitMqHandler mqHandler;
	
	/**
	 * 初始化任务记录
	 * @return
	 */
	public boolean initTaskApplyInfo(TaskApplyInfoDO taskApplyInfoDO) {
		Date taskExpireTime = getTaskExpireTime(taskApplyInfoDO);
		taskApplyInfoDO.setExpireTime(taskExpireTime);
		taskApplyInfoDO.setGmtCreate(new Date());
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
		TaskTypeInfoDO taskTypeInfoDo = taskTypeInfoService.get(taskApplyInfoDO.getTaskTypeId());
		int interval = taskTypeInfoDo.getExpirtTime();
		Date startTime = taskApplyInfoDO.getStartTime();
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		cal.add(Calendar.MINUTE, interval);
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
	
	
	
	
}
