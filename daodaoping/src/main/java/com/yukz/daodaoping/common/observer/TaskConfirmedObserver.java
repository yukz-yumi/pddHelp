package com.yukz.daodaoping.common.observer;

import java.util.Observable;
import java.util.Observer;

import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private RedisHandler redisHandler;
	
	private String getStoreKey(TaskApplyInfoDO taskInfo) {
		return "agent_"+taskInfo.getAgentId()+":task_id"+taskInfo.getId();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		TaskApplyInfoDO taskInfo = (TaskApplyInfoDO)arg;
		logger.info("监听到 任务 [taskId:{}]已经被确认,准备进入通知发单流程",taskInfo.getId());
		String key = getStoreKey(taskInfo);
		redisHandler.set(key, taskInfo.getTaskNumber());
		logger.debug("已将待完成的数量同步到redis,并准备开始发单");
		// TODO 走微信的通知流程
	}

}
