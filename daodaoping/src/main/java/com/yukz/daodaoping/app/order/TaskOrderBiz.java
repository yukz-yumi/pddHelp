package com.yukz.daodaoping.app.order;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yukz.daodaoping.common.SerialNumGenerator;
import com.yukz.daodaoping.order.domain.OrderInfoDO;
import com.yukz.daodaoping.order.service.OrderInfoService;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;
import com.yukz.daodaoping.task.domain.TaskTypeInfoDO;
import com.yukz.daodaoping.task.service.TaskTypeInfoService;
import com.yukz.daodaoping.user.service.UserInfoService;

/**
 * 任务订单处理类
 * @author micezhao
 *
 */
@Service
public class TaskOrderBiz {
	
	private static final String TIMEFORMAT = "yyyyMMddHH";

	private static final int ORDER_DELAY_CLOSE_HOURS = 24;
	
	@Autowired
	private OrderInfoService orderInfoService;
	
	@Value("${serial.prefix.order}")
	private String prefix;
	
	@Autowired
	private TaskTypeInfoService taskTypeInfoService;
	
	@Autowired
	private SerialNumGenerator generator;
	
	public OrderInfoDO initOrder(TaskApplyInfoDO taskApplyInfo) {
		OrderInfoDO item = new OrderInfoDO();
		Long orderId =Long.valueOf(generator.getSerialBizId(prefix, TIMEFORMAT, 4));
		item.setOrderId(orderId);
		item.setUserId(taskApplyInfo.getUserId());
		item.setAgentId(taskApplyInfo.getAgentId());
		item.setTaskId(taskApplyInfo.getId());
		Long totalAmount = priceCount(taskApplyInfo);
		item.setTotalAmount(totalAmount);
		setOrderExpireTime(taskApplyInfo);
		item.setOrderStatus(OrderEnum.UNPAID.getCode());
		orderInfoService.save(item);
		return item;
	}
	
	public Long priceCount (TaskApplyInfoDO taskApplyInfo) {
		Long taskTypeId = taskApplyInfo.getTaskTypeId();
		TaskTypeInfoDO taskTypeInfo = taskTypeInfoService.get(taskTypeId);
		int price = taskTypeInfo.getPrice();
		int taskApplyNum = taskApplyInfo.getTaskNumber();
		BigDecimal totalAmount = new BigDecimal(price).multiply(new BigDecimal(taskApplyNum));
		return totalAmount.longValue();
	}
	
	public void setOrderExpireTime (TaskApplyInfoDO taskApplyInfo) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR, ORDER_DELAY_CLOSE_HOURS);
		taskApplyInfo.setExpireTime(cal.getTime());
	}
	
	public void getOrderDiscountInfo(TaskApplyInfoDO taskApplyInfo) {
		Long taskId = taskApplyInfo.getId();
		
	}
	
}
