package com.yukz.daodaoping.app.order;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yukz.daodaoping.app.enums.IsAllowEnum;
import com.yukz.daodaoping.common.SerialNumGenerator;
import com.yukz.daodaoping.common.amqp.AmqpHandler;
import com.yukz.daodaoping.common.amqp.MqConstants;
import com.yukz.daodaoping.discount.domain.TaskDiscountInfoDO;
import com.yukz.daodaoping.discount.service.TaskDiscountInfoService;
import com.yukz.daodaoping.order.domain.OrderInfoDO;
import com.yukz.daodaoping.order.service.OrderInfoService;
import com.yukz.daodaoping.system.domain.SysSetDO;
import com.yukz.daodaoping.system.service.SysSetService;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;
import com.yukz.daodaoping.task.domain.TaskTypeInfoDO;
import com.yukz.daodaoping.task.enums.PlatformEnum;
import com.yukz.daodaoping.task.service.TaskTypeInfoService;

/**
 * 任务订单处理类
 * @author micezhao
 *
 */
@Service
public class TaskOrderBiz {
	
	private static final String TIMEFORMAT = "yyyyMMddHH";

//	private static final int ORDER_DELAY_CLOSE_HOURS = 24;
//	
//	private static final int ORDER_DELAY_CLOSE_MIN = 10;
	
	@Value("${ttl.order}")
	private int ttl;
	
	@Autowired
	private OrderInfoService orderInfoService;
	
	@Value("${serial.prefix.order}")
	private String prefix;
	
	@Autowired
	private TaskTypeInfoService taskTypeInfoService;
	
	@Autowired
	private SerialNumGenerator generator;
	
	@Autowired
	private SysSetService sysSetService;
	
	private static final String TTL_ORDER = "ttl_order";
	
	@Autowired
	private TaskDiscountInfoService taskDiscountInfoService;
	
	
	public OrderInfoDO initOrder(TaskApplyInfoDO taskApplyInfo) {
		OrderInfoDO item = new OrderInfoDO();
		Long orderId =Long.valueOf(generator.getSerialBizId(prefix, TIMEFORMAT, 4));
		item.setOrderId(orderId);
		item.setUserId(taskApplyInfo.getUserId());
		item.setAgentId(taskApplyInfo.getAgentId());
		item.setTaskId(taskApplyInfo.getId());
		Long totalAmount = priceCount(taskApplyInfo);
		item.setTotalAmount(totalAmount);
		item.setOrderStatus(OrderEnum.UNPAID.getCode());
		item.setGmtCreate(new Date());
		getDiscountPrice(taskApplyInfo.getTaskTypeId(),item);
		setOrderExpireTime(item);
		orderInfoService.save(item);
		return item;
	}
	
	private Long priceCount (TaskApplyInfoDO taskApplyInfo) {
		Long taskTypeId = taskApplyInfo.getTaskTypeId();
		TaskTypeInfoDO taskTypeInfo = taskTypeInfoService.get(taskTypeId);
		int price = taskTypeInfo.getPrice();
		int taskApplyNum = taskApplyInfo.getTaskNumber();
		BigDecimal totalAmount = new BigDecimal(price).multiply(new BigDecimal(taskApplyNum));
		return totalAmount.longValue();
	}
	
	private void setOrderExpireTime (OrderInfoDO item) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(item.getGmtCreate());
		SysSetDO sysSet = sysSetService.getByKey(TTL_ORDER, PlatformEnum.PDD.getCode(), null,item.getAgentId() );
		cal.add(Calendar.MILLISECOND, Integer.valueOf(sysSet.getSetValue())); 
		item.setExpireTime(cal.getTime());
	}
	
	private void getDiscountPrice(Long taskTypeId,OrderInfoDO orderInfoDO) {
		Map<String,Object> query = new HashMap<String,Object>();
		query.put("taskId", taskTypeId);
		query.put("allowed", IsAllowEnum.YES.getStatus());
		List<TaskDiscountInfoDO> discountList = taskDiscountInfoService.list(query);
		int DiscountRate = 0;
		if(!discountList.isEmpty()) {
			DiscountRate = discountList.get(0).getDiscountRate();	
		}
		Long totalAmount = orderInfoDO.getTotalAmount();
		Integer subPrice = new BigDecimal(totalAmount).multiply(new BigDecimal(DiscountRate/100))
				.intValue();
		orderInfoDO.setDiscount(subPrice);
		Long paymentPrice = new BigDecimal(orderInfoDO.getTotalAmount())
				.subtract(new BigDecimal(orderInfoDO.getDiscount())).longValue();
		orderInfoDO.setPaymentAmount(paymentPrice);
	}
	
	
}
