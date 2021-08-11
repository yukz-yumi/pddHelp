package com.yukz.daodaoping.app.fund;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yukz.daodaoping.app.fund.enums.FundEnums;
import com.yukz.daodaoping.common.amqp.AmqpHandler;
import com.yukz.daodaoping.common.amqp.MqConstants;
import com.yukz.daodaoping.fund.domain.FundTransferInfoDO;
import com.yukz.daodaoping.fund.service.FundTransferInfoService;

@RequestMapping("/appInt/fund/")
@RestController
public class FundCtrl {

	public static final Logger logger = LoggerFactory.getLogger(FundCtrl.class);

	@Autowired
	private FundTransferInfoService fundTransferInfoService;

	@Autowired
	private FundBiz fundBiz;
	
	@Autowired
	private AmqpHandler amqpHandler;

	private static final String RETURN_OK = "SUCCESS";

	private static final String RETURN_ERR = "FAIL";

	private static final String RETURN_OK_MSG = "OK";

	private static final String RETURN_ERR_MSG = "ERROR";

	@PostMapping("/wxpay/test/notify")
	public void wxpayLocalNotify(HttpServletRequest request) throws Exception {
		String returnCode = request.getParameter("result_code");
		String outTransNo = request.getParameter("out_trade_no");
		if (StringUtils.equals(returnCode, "ERROR")) {
			String returnMsg = request.getParameter("return_msg");
			logger.error("资金流水:{}支付失败,原因:{}", outTransNo, returnMsg);
		}
		Long fundTransferId = Long.valueOf(outTransNo);
		FundTransferInfoDO fundRecord = fundTransferInfoService.get(fundTransferId);
		if (fundRecord == null) {
			logger.error("资金流水:{}的记录不存在", fundTransferId);
			fundBiz.reportToWX(RETURN_ERR, RETURN_ERR_MSG);
		}
		if (FundEnums.getEnumByStatus(fundRecord.getTransStatus()) == FundEnums.SUCCESS) {
			logger.error("资金流水:{}的通知结果已经处理，不重复处理", fundTransferId);
			fundBiz.reportToWX(RETURN_OK, RETURN_OK_MSG);
		} else if (FundEnums.getEnumByStatus(fundRecord.getTransStatus()) == FundEnums.FAIL) {
			logger.error("资金流水:{}的已经为失败状态,请核查结果", fundTransferId);
			fundBiz.reportToWX(RETURN_ERR, RETURN_ERR_MSG);
		} else {
			fundBiz.fundInSuccessProcess(fundRecord);
//			fundBiz.reportToWX(RETURN_OK, RETURN_OK_MSG);
			// 推送到消息中间件
			amqpHandler.sendToDirectQueue(MqConstants.DIRECT_EXCHANGE, MqConstants.ORDER_ROUTER_KEY, fundRecord.getOrderId());
		}

	}
}
