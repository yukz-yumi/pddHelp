package com.yukz.daodaoping.app.order;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yukz.daodaoping.app.auth.vo.UserAgent;
import com.yukz.daodaoping.app.task.enums.TaskStatusEnum;
import com.yukz.daodaoping.common.utils.R;
import com.yukz.daodaoping.order.domain.OrderInfoDO;
import com.yukz.daodaoping.order.service.OrderInfoService;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;
import com.yukz.daodaoping.task.service.TaskApplyInfoService;

@RequestMapping("/appInt/order")
@RestController
public class OrderCtrl {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderCtrl.class);
	
	@Autowired
	private OrderInfoService orderInfoService;
	
	@Autowired
	private TaskApplyInfoService taskApplyInfoService;
	
	@GetMapping
	public R getOrderList(UserAgent userAgent,HttpServletRequest request) {
		Long userId = userAgent.getUserId();
		Long agentId = userAgent.getAgentId();
		
		int pageNum = Integer.valueOf(request.getParameter("pageNum"));
		int pageSize =Integer.valueOf( request.getParameter("pageSize"));
		String orderStatus = request.getParameter("status");
		String timeScope = request.getParameter("scope");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("userId", userId);
		paramMap.put("agentId", agentId);
		if (StringUtils.isNotBlank(orderStatus)) {
			paramMap.put("orderStatus", orderStatus);
		}
		Date endDate = new Date();
		if (StringUtils.isNotBlank(timeScope)) {
			endDate = getEndTime(timeScope);
		} else {
			endDate = getEndTime("week");
		}
		paramMap.put("endTime", endDate);
		List<OrderInfoDO> list = orderInfoService.list(paramMap);
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<OrderInfoDO> pageResult = new PageInfo<OrderInfoDO>(list);
		return R.ok().put("data", pageResult);
	}
	
	@PutMapping("/cancel/{id}")
	public R cancelOrder(@PathVariable("id") Long id,UserAgent userAgent) {
		Long userId = userAgent.getUserId();
		Long agentId = userAgent.getAgentId();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("agentId", agentId);
		map.put("id", id);
		List<OrderInfoDO> list = orderInfoService.list(map);
		OrderInfoDO orderInfo = new OrderInfoDO();
		
		if(list.isEmpty()) {
			return R.error("订单不存在");
		}
		orderInfo = list.get(0);
		if(orderInfo.getOrderStatus().equals(OrderEnum.CANCELED.getCode())) {
			return R.ok();
		}
		orderInfo.setOrderStatus(OrderEnum.CANCELED.getCode());
		orderInfoService.update(orderInfo);
		// 异步线程取消订单对应的任务
		new AsynCancelTask(orderInfo.getTaskId(),taskApplyInfoService).run();
		return R.ok();
	}
	
	/**
	 * 支付参数
	 * @param id
	 * @param userAgent
	 * @return
	 */
	@PutMapping("/pay/{id}")
	public R payOrder(@PathVariable("id") Long id,UserAgent userAgent) {
		// TODO 生成支付参数
		OrderInfoDO orderInfo = orderInfoService.get(id);
		
		String paymentParam = "<xml>\n"
				+ "   <return_code><![CDATA[SUCCESS]]></return_code>\n"
				+ "   <return_msg><![CDATA[OK]]></return_msg>\n"
				+ "   <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\n"
				+ "   <sub_appid><![CDATA[wx2421b1c4370ec11b]]></sub_appid>\n"
				+ "   <mch_id><![CDATA[10000100]]></mch_id>\n"
				+ "   <sub_mch_id>![CDATA[10000101]]></sub_mch_id>\n"
				+ "   <nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str>\n"
				+ "   <sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign>\n"
				+ "   <result_code><![CDATA[SUCCESS]]></result_code>\n"
				+ "   <prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id>\n"
				+ "   <trade_type><![CDATA[JSAPI]]></trade_type>\n"
				+ "</xml>";
		
		return R.ok().put("data", paymentParam);
	}
	
	public Date getEndTime(String scope) {
		Calendar cal = Calendar.getInstance();
		switch (scope) {
		case "week": // 一周内
			cal.add(Calendar.DAY_OF_MONTH, -7);
			break;
		case "month": // 一个月
			cal.add(Calendar.MONTH, -1);
			break;
		case "quarter":
			cal.add(Calendar.MONTH, -3);
			break;
		case "halfyear":
			cal.add(Calendar.MONTH, -6);
			break;
		default:
			cal.add(Calendar.DAY_OF_MONTH, -7);
			break;
		}
		return cal.getTime();
	}
	
	/**
	 * 异步取消任务的接口
	 * @author micezhao
	 *
	 */
	public class AsynCancelTask implements Runnable {
		private Long taskId;
		private TaskApplyInfoService taskApplyInfoService;
		
		public AsynCancelTask(Long taskId, TaskApplyInfoService taskApplyInfoService) {
			super();
			this.taskId = taskId;
			this.taskApplyInfoService = taskApplyInfoService;
		}

		public void run() {
			TaskApplyInfoDO taskInfo = taskApplyInfoService.get(taskId);
			taskInfo.setTaskStatus(TaskStatusEnum.CANCEL.getStatus());
			taskInfo.setGmtModify(new Date());
			int i = taskApplyInfoService.update(taskInfo);
			if(i!=1) {
				logger.error("taskId:{}的任务取消失败",taskId);				
			}
		}
	}
	
}
