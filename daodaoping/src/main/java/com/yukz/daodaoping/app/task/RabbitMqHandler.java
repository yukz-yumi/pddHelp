package com.yukz.daodaoping.app.task;

import org.springframework.stereotype.Service;

import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;

/**
 * 消息中间件的处理器
 * @author micezhao
 *
 */
@Service
public class RabbitMqHandler {
	
	public void sendTaskApplyInfo2DelayQueue(TaskApplyInfoDO taskApplyInfo) {
		
	}
	
}
