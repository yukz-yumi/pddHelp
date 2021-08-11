package com.yukz.daodaoping.common.amqp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yukz.daodaoping.app.order.OrderEnum;
import com.yukz.daodaoping.order.domain.OrderInfoDO;
import com.yukz.daodaoping.order.service.OrderInfoService;

@Service
public class Receivers {
	
	private static final Logger logger = LoggerFactory.getLogger(Receivers.class);
	
	@Autowired
	private OrderInfoService orderInfoService;
	
	@RabbitListener(queues = MqConstants.DIRECT_ORDER_QUEUE)
	public void OrderQueueListener(Long orderId) {
		logger.info("监听到队列{}的消息,orderId:{}",MqConstants.DIRECT_ORDER_QUEUE,orderId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orderId",orderId);
		OrderInfoDO orderInfo = orderInfoService.list(map).get(0);
		orderInfo.setOrderStatus(OrderEnum.PAID.getCode());
		orderInfo.setGmtModify(new Date());
		orderInfoService.update(orderInfo);
		
		// TODO 订单更新后，准备更新任务并发布
	}
	
}
