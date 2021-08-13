package com.yukz.daodaoping.app.order;

import java.io.BufferedReader;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import com.yukz.daodaoping.app.auth.vo.UserAgent;
import com.yukz.daodaoping.app.fund.FundBiz;
import com.yukz.daodaoping.app.fund.FundRequest;
import com.yukz.daodaoping.app.fund.enums.FundBizEnum;
import com.yukz.daodaoping.app.fund.enums.FundEnums;
import com.yukz.daodaoping.app.fund.enums.FundTransTypeEnum;
import com.yukz.daodaoping.app.fund.wxpay.DDPWXpayConfig;
import com.yukz.daodaoping.app.task.enums.TaskStatusEnum;
import com.yukz.daodaoping.common.utils.R;
import com.yukz.daodaoping.fund.domain.FundTransferInfoDO;
import com.yukz.daodaoping.fund.service.FundTransferInfoService;
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

	@Autowired
	private FundBiz fundBiz;
	
	@GetMapping
	public R getOrderList(UserAgent userAgent, HttpServletRequest request) {
		Long userId = userAgent.getUserId();
		Long agentId = userAgent.getAgentId();

		int pageNum = Integer.valueOf(request.getParameter("pageNum"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String orderStatus = request.getParameter("status");
		String timeScope = request.getParameter("scope");
		Map<String, Object> paramMap = new HashMap<String, Object>();
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
	public R cancelOrder(@PathVariable("id") Long id, UserAgent userAgent) {
		Long userId = userAgent.getUserId();
		Long agentId = userAgent.getAgentId();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("agentId", agentId);
		map.put("id", id);
		List<OrderInfoDO> list = orderInfoService.list(map);
		OrderInfoDO orderInfo = new OrderInfoDO();

		if (list.isEmpty()) {
			return R.error("订单不存在");
		}
		orderInfo = list.get(0);
		if (orderInfo.getOrderStatus().equals(OrderEnum.CANCELED.getCode())) {
			return R.ok();
		}
		orderInfo.setOrderStatus(OrderEnum.CANCELED.getCode());
		orderInfoService.update(orderInfo);
		// 异步线程取消订单对应的任务
		new AsynCancelTask(orderInfo.getTaskId(), taskApplyInfoService).run();
		return R.ok();
	}

	/**
	 * 支付参数
	 * 
	 * @param id
	 * @param userAgent
	 * @return
	 */
	@PutMapping("/pay/{id}")
	public R payOrder(@PathVariable("id") Long id, UserAgent userAgent) {
		OrderInfoDO orderInfo = orderInfoService.get(id);
		if (orderInfo == null) {
			return R.error("订单不存在");
		}
		FundTransferInfoDO fundTransferRecord = null;
		FundRequest fundRequest = new FundRequest();
		fundTransferRecord = (FundTransferInfoDO) fundBiz.getTransferReord(FundTransTypeEnum.FUND_IN,
				orderInfo.getOrderId(), FundEnums.WAIT.getStatus());
		if (fundTransferRecord == null) {
			fundRequest.setAgentId(userAgent.getAgentId());
			fundRequest.setOpenId(userAgent.getOpenId());
			fundRequest.setUserId(userAgent.getUserId());
			fundRequest.setBizType(FundBizEnum.FUND_PAY.getBizType());
			fundRequest.setFundAmount(orderInfo.getPaymentAmount());
			fundRequest.setTransType(FundTransTypeEnum.FUND_IN.getType());
			fundRequest.setOrderId(orderInfo.getOrderId());
			fundTransferRecord = fundBiz.initFundTransRecord(fundRequest);
		}
		if (fundTransferRecord == null) {
			R.error("支付记录初始化失败");
		}
		String paymentParam = fundBiz.fundParamGenerator(fundTransferRecord);
		
		return R.ok().put("data", paymentParam);
	}
	
	
	
	
	/**
	 * 微信支付回调地址
	 * 
	 * @param map
	 * @throws Exception
	 */
	@PostMapping("/wxpay/notify")
	public void wxpayNotify(HttpServletRequest request) throws Exception {

		String return_code = "FALSE";
		String return_msg = "ERROR";

		BufferedReader br = request.getReader();
		String str = "";
		String notifyData = "";
		while ((str = br.readLine()) != null) {
			notifyData += str;
		}

		logger.info("收到微信支付回调通知:{}", notifyData);
		WXPayConfig config = new DDPWXpayConfig();
		WXPay wxpay = new WXPay(config);
		Map<String, String> notifyMap = WXPayUtil.xmlToMap(notifyData); // 转换成map
		boolean returnStatus = checkReturnCode(notifyMap);
		if (!returnStatus) {
			logger.error("通知返回结果失败,不进行后续操作");
		} else {
			if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
				String outTransNo = notifyMap.get("out_trade_no");
				String resultCode = notifyMap.get("result_code");
				// TODO 具体的业务处理流程

			} else {
				logger.error("签名校验失败");
			}
		}
	}

	public boolean checkReturnCode(Map<String, String> notifyMap) {
		String returnCode = notifyMap.get("return_code");
		String returnMsg = notifyMap.get("return_msg");
		if (!StringUtils.equals(returnCode, "SUCCESS")) {
			logger.error("接收通知失败,原因:{}", returnMsg);
			return false;
		} else {
			return true;
		}
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
	 * 
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
			if (i != 1) {
				logger.error("taskId:{}的任务取消失败", taskId);
			}
		}
	}

}
