package com.yukz.daodaoping.common.observer;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yukz.daodaoping.app.task.TaskConstants;
import com.yukz.daodaoping.app.task.TaskExecuteBiz;
import com.yukz.daodaoping.system.config.RedisHandler;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;

/**
 * 任务确认的观察者
 * @author micezhao
 *
 */
@Service
public class TaskConfirmedObserver implements Observer {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskConfirmedObserver.class);
	
	@Autowired
	private RedissonClient redissonClient;
	
	@Autowired
	private RedisHandler redisHandler;
	
	@Autowired
	private TaskExecuteBiz taskExecuteBiz;
	
	
	@Override
	public void update(Observable o, Object arg) {
		TaskApplyInfoDO taskInfo = (TaskApplyInfoDO)arg;
		logger.info("监听到 任务 [taskId:{}]已经被确认,准备进入通知发单流程",taskInfo.getId());
		String key = taskExecuteBiz.taskRemaminKeyGenerator(taskInfo.getAgentId(),taskInfo.getId());
		RLock rlock = redissonClient.getLock(key);
		try {
			rlock.tryLock(5, TimeUnit.MINUTES);
			redisHandler.hmSet(TaskConstants.MAP_REMAIN, key, taskInfo.getTaskNumber());
			logger.debug("已将待完成的数量同步到redis,并准备开始发单");
			// TODO 走微信的通知流程
			logger.info("向用户进行任务发布通知");
		}catch(InterruptedException exn) {
			logger.error("加锁失败，释放锁");
		}finally {
			rlock.unlock();
		}
	}

}
