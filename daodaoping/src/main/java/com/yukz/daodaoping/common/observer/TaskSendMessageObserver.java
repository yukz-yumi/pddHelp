package com.yukz.daodaoping.common.observer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yukz.daodaoping.app.order.vo.OrderDetailVO;
import com.yukz.daodaoping.app.task.enums.TaskStatusEnum;
import com.yukz.daodaoping.app.wx.WXService;
import com.yukz.daodaoping.app.wx.request.MessageRequest;
import com.yukz.daodaoping.order.domain.OrderInfoDO;
import com.yukz.daodaoping.order.service.OrderInfoService;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;
import com.yukz.daodaoping.task.service.TaskApplyInfoService;
import com.yukz.daodaoping.user.domain.UserInfoDO;
import com.yukz.daodaoping.user.service.UserInfoService;

@Service
public class TaskSendMessageObserver implements Observer {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskSendMessageObserver.class);
	
	@Autowired
	private WXService wxService;
	
	@Autowired
	private OrderInfoService orderInfoService;
	
	@Autowired
	private TaskApplyInfoService taskApplyInfoService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Override
	public void update(Observable arg0, Object arg1) {
		OrderInfoDO orderInfo = (OrderInfoDO)arg1;
		logger.info("监听到订单:orderId={}状态的变化...",orderInfo.getOrderId());
		TaskApplyInfoDO taskApplyInfo = taskApplyInfoService.get(orderInfo.getTaskId());
		if(!taskApplyInfo.getTaskStatus().equals(TaskStatusEnum.PENDING.getStatus())) {
			try {
				Thread.sleep(5000);
				taskApplyInfo = taskApplyInfoService.get(orderInfo.getTaskId());
				if(!taskApplyInfo.getTaskStatus().equals(TaskStatusEnum.PENDING.getStatus())) {
					logger.error("再次检查任务taskId:{},任务状态为:{},当前任务状态无法进行通知",taskApplyInfo.getId(),taskApplyInfo.getTaskStatus());
					return;
				}
			} catch (InterruptedException e) {
				logger.error("线程休眠失败,原因:{}",e.getMessage());
				return;
			}
		}
		
		logger.info("taskId:{}的任务开启成功...准备向发单用户发送任务确认成功的通知",taskApplyInfo.getId());
		OrderDetailVO orderDetail  = orderInfoService.getOrderDetailById(orderInfo.getId());
		MessageRequest request = new MessageRequest();
		request.setAppId(orderInfo.getAgentId());
		request.setAmount(String.valueOf(orderDetail.getPaymentAmount()));
		Long userId = orderDetail.getUserId();
		Map<String,Object> userInfoMap = new HashMap<String,Object>();
		userInfoMap.put("userId",userId);
		UserInfoDO userInfo = userInfoService.list(userInfoMap).get(0);
		request.setOpenId(userInfo.getOpenId());
		request.setTitle(orderDetail.getTaskTypeDesc());
		request.setDetail("订单编号:"+orderDetail.getOrderId());
		request.setMemo("任务预计完成时间为:"+new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(taskApplyInfo.getExpireTime()));
		wxService.taskPublishMsgSend(request);
	}
	

}
