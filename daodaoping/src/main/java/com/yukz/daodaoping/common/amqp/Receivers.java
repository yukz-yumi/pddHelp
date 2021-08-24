package com.yukz.daodaoping.common.amqp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch.TaskInfo;

import com.yukz.daodaoping.app.order.OrderEnum;
import com.yukz.daodaoping.app.task.TaskExecuteBiz;
import com.yukz.daodaoping.app.task.enums.TaskAcceptionStatusEunm;
import com.yukz.daodaoping.app.task.enums.TaskStatusEnum;
import com.yukz.daodaoping.common.observer.RealSubject;
import com.yukz.daodaoping.common.observer.TaskConfirmedObserver;
import com.yukz.daodaoping.common.observer.TaskSendMessageObserver;
import com.yukz.daodaoping.order.domain.OrderInfoDO;
import com.yukz.daodaoping.order.service.OrderInfoService;
import com.yukz.daodaoping.task.domain.TaskAcceptInfoDO;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;
import com.yukz.daodaoping.task.service.TaskAcceptInfoService;
import com.yukz.daodaoping.task.service.TaskApplyInfoService;

@Service
public class Receivers {

	private static final Logger logger = LoggerFactory.getLogger(Receivers.class);

	@Autowired
	private OrderInfoService orderInfoService;

	@Autowired
	private TaskApplyInfoService taskApplyInfoService;
	
	@Autowired
	private TaskAcceptInfoService taskAcceptInfoService;

	@Autowired
	private AmqpHandler amqpHandler;
	
	@Autowired
	private TaskConfirmedObserver taskConfirmedObserver;
	
	@Autowired
	private TaskSendMessageObserver taskSendMessageObserver;

	@Autowired
	private TaskExecuteBiz taskExecuteBiz;

	@RabbitListener(queues = MqConstants.DIRECT_ORDER_QUEUE)
	public void OrderConfirmQueueListener(Long orderId) throws Exception {
		logger.info("监听到队列{}的消息,orderId:{}", MqConstants.DIRECT_ORDER_QUEUE, orderId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		if (orderInfoService.list(map).isEmpty()) {
			logger.warn("待处理的订单id:{}不存在,废弃此消息", orderId);
			return;
		}
		OrderInfoDO orderInfo = orderInfoService.list(map).get(0);
		if (orderInfo.getOrderStatus().equals(OrderEnum.PAID.getCode())) {
			logger.info("订单orderId:{}已处理成功,不在重复处理", orderId);
			return;
		} else if (orderInfo.getOrderStatus().equals(OrderEnum.UNPAID.getCode())) {
			logger.info("准备更新orderId:{}的订单", orderId);
			orderInfo.setOrderStatus(OrderEnum.PAID.getCode());
			orderInfo.setGmtModify(new Date());
			orderInfoService.update(orderInfo);
			logger.info("orderId:{}的订单状态更新成功", orderId);
		} else {
			logger.info("orderId:{}的订单状态为{},无法处理", OrderEnum.getByCode(orderInfo.getOrderStatus()).getDesc());
			return;
		}

		// 交给订单处理类处理
		amqpHandler.sendToDirectQueue(MqConstants.TASK_ROUTER_KEY, orderInfo.getTaskId());
		RealSubject subject = new RealSubject();
		subject.addObserver(taskSendMessageObserver);
		subject.makeChanged(orderInfo);
	}

	/**
	 * 更新任务为开始执行
	 * 
	 * @param taskId
	 * @throws Exception
	 */
	@RabbitListener(queues = MqConstants.DIRECT_TASK_QUEUE)
	public void TaskConfirmQueueListener(Long taskId) throws Exception {
		logger.info("监听到队列{}的消息,taskId:{}", MqConstants.DIRECT_TASK_QUEUE, taskId);
		TaskApplyInfoDO taskInfo = taskApplyInfoService.get(taskId);
		if (taskInfo == null) {
			logger.warn("待处理的任务id:{}不存在,废弃此消息", taskId);
			return;
		}
		if (taskInfo.getTaskStatus().equals(TaskStatusEnum.PENDING.getStatus())) {
			logger.info("任务taskId:{}已处理成功,不在重复处理", taskId);
			return;
		} else if (taskInfo.getTaskStatus().equals(TaskStatusEnum.SUSPEND.getStatus())) {
			logger.info("准备更新taskId:{}的任务", taskId);
			taskInfo.setTaskStatus(TaskStatusEnum.PENDING.getStatus());
			taskInfo.setStartTime(new Date());
			Date expireDate = taskExecuteBiz.getTaskExpireTime(taskInfo);
			taskInfo.setExpireTime(expireDate);
			taskInfo.setGmtModify(new Date());
			taskApplyInfoService.update(taskInfo);
			// 向监听者发送消息
			RealSubject subject = new RealSubject();
			subject.addObserver(taskConfirmedObserver);
			subject.makeChanged(taskInfo);
			amqpHandler.sendDelayMessage(taskId, MqConstants.TASK_EXPIRE_ROUTER_KEY);
			logger.info("任务taskId:{}状态更新成功", taskId);
		} else {
			logger.info("taskId:{}的订单状态为{},无法处理", TaskStatusEnum.getEnumByStatus(taskInfo.getTaskStatus()).getDesc());
			throw new Exception("任务状态更新失败");
		}
	}

	@RabbitListener(queues = MqConstants.ORDER_TTL_QUEUE)
	public void ExpireOrderListener(Long id) throws Exception {
		logger.info("监听来自订单过期关闭队列{}的消息,待处理的订单id:{}", MqConstants.ORDER_EXPIRE_QUEUE, id);
		OrderInfoDO orderInfo = orderInfoService.get(id);
		if (orderInfo == null) {
			logger.warn("待处理的订单id:{}不存在,废弃此消息", id);
			return;
		}
		if (orderInfo.getOrderStatus().equals(OrderEnum.EXPIRED.getCode())) {
			logger.info("订单Id:{}/orderId:{}以为过期状态,不在重复处理", orderInfo.getId(), orderInfo.getOrderId());
			return;
		} else if (orderInfo.getOrderStatus().equals(OrderEnum.UNPAID.getCode())) {
			logger.info("准备更新订单Id:{}/orderId:{}的订单", orderInfo.getId(), orderInfo.getOrderId());
			orderInfo.setOrderStatus(OrderEnum.EXPIRED.getCode());
			orderInfo.setGmtModify(new Date());
			orderInfoService.update(orderInfo);
			logger.info("准备更新订单Id:{}/orderId:{}的订单状态更新成功", orderInfo.getId(), orderInfo.getOrderId());
			taskExecuteBiz.close(orderInfo.getTaskId());
		} else {
			logger.info("当前订单{}状态为:{},无需处理", orderInfo.getOrderId(), orderInfo.getOrderStatus());
		}
	}

	@RabbitListener(queues = MqConstants.TASK_TTL_QUEUE)
	public void ExpireTaskListener(Long id) throws Exception {
		logger.info("监听来自任务过期队列{}的消息,待处理的任务id:{}", MqConstants.TASK_EXPIRE_QUEUE, id);
		TaskApplyInfoDO taskInfo = taskApplyInfoService.get(id);
		if (taskInfo == null) {
			logger.warn("待处理的任务id:{}不存在,废弃此消息", id);
			return;
		}
		if (taskInfo.getTaskStatus().equals(TaskStatusEnum.END.getStatus())) {
			logger.info("任务taskId:{}已结束", id);
			return;
		} else if (taskInfo.getTaskStatus().equals(TaskStatusEnum.PENDING.getStatus())) {
			logger.info("准备结束taskId{}的任务", id);
			taskInfo.setTaskStatus(TaskStatusEnum.END.getStatus());
			taskInfo.setGmtModify(new Date());
			taskApplyInfoService.update(taskInfo);
			logger.info("任务taskId:{}已结束", id);
		} else {
			logger.info("任务taskId:{}的状态为:{}无需处理", id, taskInfo.getTaskStatus());
		}
	}
	
	@RabbitListener(queues = MqConstants.TASK_TAKE_TTL_QUEUE)
	public void ExpireTaskTakeListener(Long id){
		 TaskAcceptInfoDO takeInfo = taskAcceptInfoService.get(id);
		 if(takeInfo == null) {
			 logger.warn("待处理的接单id:{}不存在,废弃此消息", id);
			 return;
		 }
		 if(takeInfo.getTaskStatus().equals( TaskAcceptionStatusEunm.EXPIRED.getStatus())) {
			 logger.info("接单任务taskAcceptionId:{}已过期,不再重复处理", id);
			 return;
		 } else if(takeInfo.getTaskStatus().equals( TaskAcceptionStatusEunm.PENDING.getStatus())) {
			 logger.info("准备过期接单任务id{}的任务", id);
			 takeInfo.setTaskStatus(TaskAcceptionStatusEunm.EXPIRED.getStatus());
			 takeInfo.setGmtModify(new Date());
			 taskAcceptInfoService.update(takeInfo);
			 logger.info("接单任务id{}已设置为过期", id);
			 logger.info("准备进行偿还...");
			 taskExecuteBiz.repay(takeInfo.getAgentId(), takeInfo.getTaskId());
		 }else {
			 logger.info("接单任务id{}的状态为:{}无需处理", id, takeInfo.getTaskStatus()); 
		 }
	}
	
}
