package com.yukz.daodaoping.app.task.threads;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yukz.daodaoping.app.task.RabbitMqHandler;
import com.yukz.daodaoping.common.amqp.AmqpHandler;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;

public class SetTaskExecutionThread implements Callable<Boolean> {

	private static final Logger logger = LoggerFactory.getLogger(SetTaskExecutionThread.class);

	private String threadName;

	private List<TaskApplyInfoDO> taskApplyInfoDOList;

	private RedissonClient redissonClient;

	private AmqpHandler mqHandler;

	

	public SetTaskExecutionThread(String threadName, List<TaskApplyInfoDO> taskApplyInfoDOList,
			RedissonClient redissonClient, AmqpHandler mqHandler) {
		super();
		this.threadName = threadName;
		this.taskApplyInfoDOList = taskApplyInfoDOList;
		this.redissonClient = redissonClient;
		this.mqHandler = mqHandler;
	}

	@Override
	public Boolean call() throws Exception {
		for (TaskApplyInfoDO taskApplyInfoDO : taskApplyInfoDOList) {
			setCurrentDayExecution(taskApplyInfoDO);			
		}
		return true;
	}

	/**
	 * 设定当前日需要执行的任务
	 * 
	 * @param taskApplyInfoDO
	 */
	public void setCurrentDayExecution(TaskApplyInfoDO taskApplyInfoDO) {
		logger.debug(threadName + "准备同步发单申请到延迟队列");
		RLock lock = this.getRedisLock(taskApplyInfoDO.getId(), redissonClient);
		try {
			lock.tryLock(3, 10, TimeUnit.SECONDS);
			// TODO 向rq中发送消息
			mqHandler.sendTaskApplyInfo2DelayQueue(taskApplyInfoDO);
		} catch (InterruptedException e) {
			logger.error("redis 加锁失败，线程被打断");
		} finally {
			lock.unlock();
		}
	}

	public RLock getRedisLock(Long taskApplyInfoId, RedissonClient redissonClient) {
		return redissonClient.getLock(String.valueOf(taskApplyInfoId));
	}

}
