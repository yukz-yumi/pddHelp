package com.yukz.daodaoping.app.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yukz.daodaoping.common.amqp.AmqpHandler;
import com.yukz.daodaoping.common.amqp.MqConstants;
import com.yukz.daodaoping.order.domain.OrderInfoDO;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;

/**
 * 消息中间件的处理器
 * @author micezhao
 *
 */
@Service
public class RabbitMqHandler {
	
	@Autowired
	private AmqpHandler amqpHandler;
	
	public void sendTaskApplyInfo2DelayQueue(TaskApplyInfoDO taskApplyInfo) {
		
	}
	
	
	public void sendOrderDelayQueue(OrderInfoDO orderInfo) {
		amqpHandler.sendDelayMessage(orderInfo, MqConstants.DELAY_ORDER_EXPIRE_ROUTER_KEY, orderInfo.getExpireTime());
	}
	
}
