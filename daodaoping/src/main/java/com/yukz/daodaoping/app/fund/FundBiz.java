package com.yukz.daodaoping.app.fund;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import com.yukz.daodaoping.app.fund.enums.FundBizEnum;
import com.yukz.daodaoping.app.fund.enums.FundEnums;
import com.yukz.daodaoping.app.fund.enums.FundTransTypeEnum;
import com.yukz.daodaoping.app.fund.wxpay.DDPWXpayConfig;
import com.yukz.daodaoping.fund.domain.FundTransferInfoDO;
import com.yukz.daodaoping.fund.domain.RefundInfoDO;
import com.yukz.daodaoping.fund.service.FundTransferInfoService;
import com.yukz.daodaoping.fund.service.RefundInfoService;

/**
 * 资金业务处理类
 * 
 * @author micezhao
 *
 */
@Service
public class FundBiz {

	private static final Logger logger = LoggerFactory.getLogger(FundBiz.class);

	@Autowired
	private FundTransferInfoService fundTransferInfoService;

	@Autowired
	private RefundInfoService refundInfoService;

	private WXPayConfig config = new DDPWXpayConfig();

	private WXPay wxpay = new WXPay(config);

	/**
	 * 初始化支付订单的流水
	 * 
	 * @param fundRequest
	 * @return
	 */
	public FundTransferInfoDO initFundTransRecord(FundRequest fundRequest) {
		FundTransferInfoDO fundTransfer = new FundTransferInfoDO();
		try {
			PropertyUtils.copyProperties(fundTransfer, fundRequest);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			return null;
		}
		fundTransfer.setGmtCreate(new Date());
		fundTransfer.setTransStatus(FundEnums.WAIT.getStatus());
		fundTransferInfoService.save(fundTransfer);
		return fundTransfer;
	}

	/**
	 * 初始化 退费订单的流水
	 * 
	 * @param fundRequest
	 * @return
	 */
	public RefundInfoDO initRefundRecord(FundRequest fundRequest) {
		RefundInfoDO refundRecord = new RefundInfoDO();
		try {
			PropertyUtils.copyProperties(refundRecord, fundRequest);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			return null;
		}
		refundRecord.setRefundStatus(FundEnums.WAIT.getStatus());
		refundRecord.setGmtCreate(new Date());
		refundInfoService.save(refundRecord);
		return refundRecord;
	}

	public <T> Object getTransferReord(FundTransTypeEnum fundTransType, Long orderId, String fundStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (fundTransType == FundTransTypeEnum.FUND_IN) {
			map.put("orderId", orderId);
			map.put("fundStatus", fundStatus);
			List<FundTransferInfoDO> list = fundTransferInfoService.list(map);
			if (list.isEmpty() || list.size() > 1) {
				return null;
			} else {
				return list.get(0);
			}
		} else if (fundTransType == FundTransTypeEnum.REFUND) {
			map.put("orderId", orderId);
			map.put("fundStatus", fundStatus);
			List<RefundInfoDO> list = refundInfoService.list(map);
			if (list.isEmpty() || list.size() > 1) {
				return null;
			} else {
				return list.get(0);
			}
		}
		return null;
	}

	/**
	 * 根据支付接口生成
	 * 
	 * @param fundTransfer
	 * @return
	 */
	public String fundParamGenerator(FundTransferInfoDO fundTransfer) {
		String paymentParam = "<xml>\n" + "   <appid>wx2421b1c4370ec43b</appid>\n" + "   <attach>支付测试</attach>\n"
				+ "   <body>JSAPI支付测试</body>\n" + "   <mch_id>10000100</mch_id>\n"
				+ "   <detail><![CDATA[{ \"goods_detail\":[ { \"goods_id\":\"iphone6s_16G\", \"wxpay_goods_id\":\"1001\", \"goods_name\":\"iPhone6s 16G\", \"quantity\":1, \"price\":528800, \"goods_category\":\"123456\", \"body\":\"苹果手机\" }, { \"goods_id\":\"iphone6s_32G\", \"wxpay_goods_id\":\"1002\", \"goods_name\":\"iPhone6s 32G\", \"quantity\":1, \"price\":608800, \"goods_category\":\"123789\", \"body\":\"苹果手机\" } ] }]]></detail>\n"
				+ "   <nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>\n"
				+ "   <notify_url>https://wxpay.wxutil.com/pub_v2/pay/notify.v2.php</notify_url>\n"
				+ "   <openid>oUpF8uMuAJO_M2pxb1Q9zNjWeS6o</openid>\n" + "   <out_trade_no>1415659990</out_trade_no>\n"
				+ "   <spbill_create_ip>14.23.150.211</spbill_create_ip>\n" + "   <total_fee>1</total_fee>\n"
				+ "   <trade_type>JSAPI</trade_type>\n" + "   <sign>0CB01533B8C1EF103065174F50BCA001</sign>\n"
				+ "</xml>";
		return paymentParam;
	}

	/**
	 * 支付成功的处理方法
	 * 
	 * @param fundRecord
	 */
	public void fundInSuccessProcess(FundTransferInfoDO fundRecord) {
		fundRecord.setTransStatus(FundEnums.SUCCESS.getStatus());
		fundRecord.setGmtModify(new Date());
		fundTransferInfoService.update(fundRecord);
	}

	public void reportToWX(String returnCode, String returnMsg) throws Exception {
		// 向微信通知返回结果
		logger.info("业务处理结束，准备向微信发起报告....");
		Map<String, String> reqData = new HashMap<String, String>();
		reqData.put("return_code", returnCode);
		reqData.put("return_msg", returnMsg);
		Map<String, String> reportResult = wxpay.report(reqData);
		logger.info("报告完成....报告结果:{}", WXPayUtil.mapToXml(reportResult));

	}

	// TODO 微信出金接口
	public boolean WXfundOut(FundTransferInfoDO fundRecord) {
		logger.info("调用外部接口...开始转账");
		// TODO 调用外部接口
		logger.info("转账成功...");
		return true;
	}

	/**
	 * 出金接口
	 * 
	 * @param fundRequest
	 * @param fundBiz     资金业务类型
	 * @throws Exception
	 */
	public void fundOutProcess(FundRequest fundRequest, FundBizEnum fundBiz) throws Exception {
		logger.info("业务类型{}准备开始出金转账...", fundBiz.getBizType());
		logger.info("初始化出金资金流水...");
		FundTransferInfoDO fundRecord = new FundTransferInfoDO();
		fundRecord.setTransStatus(FundEnums.WAIT.getStatus());
		fundRecord.setTransType(FundTransTypeEnum.FUND_OUT.getType());
		try {
			PropertyUtils.copyProperties(fundRecord, fundRequest);
			fundTransferInfoService.save(fundRecord);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			fundRecord.setTransStatus(FundEnums.FAIL.getStatus());
			fundTransferInfoService.update(fundRecord);
			logger.error("对象赋值失败");
			logger.info("更新出金资金流水状态为:【失败】");
			throw new Exception("对象赋值失败");
		}
		if (!WXfundOut(fundRecord)) {
			fundRecord.setTransStatus(FundEnums.FAIL.getStatus());
			fundTransferInfoService.update(fundRecord);
			logger.info("更新出金资金流水状态为:【失败】");
		}
		fundRecord.setTransStatus(FundEnums.SUCCESS.getStatus());
		fundTransferInfoService.update(fundRecord);
		logger.info("更新出金资金流水状态为:【成功】");
	}

	// TODO 微信出金接口
	public boolean WXRefund(FundRequest fundRequest) {
		logger.info("调用外部接口...开始转账");
		// TODO 调用外部接口
		logger.info("转账成功...");
		return true;
	}

	public void refundProcess(FundRequest fundRequest) throws Exception {
		logger.info("进入对用户编号:{}的退款流程...应退款的金额为{}", fundRequest.getUserId(), fundRequest.getFundAmount());
		logger.info("初始化退款流水...");
		RefundInfoDO refundInfo = initRefundRecord(fundRequest);
		if(!WXRefund(fundRequest)) {
			refundInfo.setRefundStatus(FundEnums.FAIL.getStatus());
			refundInfoService.update(refundInfo);
			logger.error("退费失败");
			throw new Exception("调用微信退费接口失败");
		}
		refundInfo.setRefundStatus(FundEnums.SUCCESS.getStatus());
		refundInfoService.update(refundInfo);
		logger.info("退费成功");
	}

}
