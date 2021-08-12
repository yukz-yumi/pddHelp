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
import com.yukz.daodaoping.app.task.enums.TaskStatusEnum;
import com.yukz.daodaoping.order.domain.OrderInfoDO;
import com.yukz.daodaoping.order.service.OrderInfoService;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;
import com.yukz.daodaoping.task.service.TaskApplyInfoService;

@Service
public class Receivers {
	
	private static final Logger logger = LoggerFactory.getLogger(Receivers.class);
	
	@Autowired
	private OrderInfoService orderInfoService;
	
	@Autowired
	private TaskApplyInfoService taskApplyInfoService;
	
	@Autowired
	private AmqpHandler amqpHandler;
	
	@RabbitListener(queues = MqConstants.DIRECT_ORDER_QUEUE)
	public void OrderConfirmQueueListener(Long orderId) throws Exception {
		logger.info("监听到队列{}的消息,orderId:{}",MqConstants.DIRECT_ORDER_QUEUE,orderId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orderId",orderId);
		OrderInfoDO orderInfo = orderInfoService.list(map).get(0);
		if(orderInfo.getOrderStatus().equals(OrderEnum.PAID.getCode())) {
			logger.info("订单orderId:{}已处理成功,不在重复处理",orderId);
			return;
		}else if(orderInfo.getOrderStatus().equals(OrderEnum.UNPAID.getCode())){
			logger.info("准备更新orderId:{}的订单",orderId);
			orderInfo.setOrderStatus(OrderEnum.PAID.getCode());
			orderInfo.setGmtModify(new Date());
			orderInfoService.update(orderInfo);
			logger.info("orderId:{}的订单状态更新成功",orderId);
		}else {
			logger.info("orderId:{}的订单状态为{},无法处理",OrderEnum.getByCode(orderInfo.getOrderStatus()).getDesc());
			throw new Exception("订单状态更新失败");
		}
		
		// 交给订单处理类处理
		amqpHandler.sendToDirectQueue(MqConstants.TASK_ROUTER_KEY, orderInfo.getTaskId());
	}
	
	/**
	 * 更新任务为开始执行
	 * @param taskId
	 * @throws Exception 
	 */
	@RabbitListener(queues = MqConstants.DIRECT_TASK_QUEUE)
	public void TaskConfirmQueueListener(Long taskId) throws Exception {
		logger.info("监听到队列{}的消息,taskId:{}",MqConstants.DIRECT_TASK_QUEUE,taskId);
		 TaskApplyInfoDO taskInfo = taskApplyInfoService.get(taskId);
		 if(taskInfo.getTaskStatus().equals(TaskStatusEnum.PENDING.getStatus())) {
			 logger.info("任务taskId:{}已处理成功,不在重复处理",taskId);
				return;
		 } else if(taskInfo.getTaskStatus().equals(TaskStatusEnum.SUSPEND.getStatus())) {
			 logger.info("准备更新taskId:{}的任务",taskId);
			 taskInfo.setTaskStatus(TaskStatusEnum.PENDING.getStatus());
			 taskInfo.setGmtModify(new Date());
			 taskApplyInfoService.update(taskInfo);
			 logger.info("任务taskId:{}状态更新成功",taskId);
		 } else {
			 logger.info("taskId:{}的订单状态为{},无法处理",TaskStatusEnum.getEnumByStatus(taskInfo.getTaskStatus()).getDesc());
				throw new Exception("任务状态更新失败");
		 }
	}
	
	
	@RabbitListener(queues = MqConstants.ORDER_EXPIRE_QUEUE)
	public void ExpireOrderListener(Long id) throws Exception {
		logger.info("监听来自订单过期关闭队列{}的消息,待处理的订单id:{}",MqConstants.ORDER_EXPIRE_QUEUE,id);
		OrderInfoDO orderInfo = orderInfoService.get(id);
		if(orderInfo.getOrderStatus().equals(OrderEnum.EXPIRED.getCode())) {
			 logger.info("订单Id:{}/orderId:{}以为过期状态,不在重复处理",orderInfo.getId(),orderInfo.getOrderId());
				return;
		}else if(orderInfo.getOrderStatus().equals(OrderEnum.UNPAID.getCode())) {
			 logger.info("准备更新订单Id:{}/orderId:{}的订单",orderInfo.getId(),orderInfo.getOrderId());
			orderInfo.setOrderStatus(OrderEnum.EXPIRED.getCode());
			orderInfo.setGmtModify(new Date());
			orderInfoService.update(orderInfo);
			logger.info("准备更新订单Id:{}/orderId:{}的订单状态更新成功",orderInfo.getId(),orderInfo.getOrderId());
		}
	}
}
