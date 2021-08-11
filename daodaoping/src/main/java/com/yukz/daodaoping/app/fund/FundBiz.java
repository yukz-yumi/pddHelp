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
import com.yukz.daodaoping.app.fund.enums.FundEnums;
import com.yukz.daodaoping.app.fund.enums.FundTransTypeEnum;
import com.yukz.daodaoping.app.fund.wxpay.DDPWXpayConfig;
import com.yukz.daodaoping.fund.domain.FundTransferInfoDO;
import com.yukz.daodaoping.fund.domain.RefundInfoDO;
import com.yukz.daodaoping.fund.service.FundTransferInfoService;
import com.yukz.daodaoping.fund.service.RefundInfoService;

/**
 * 资金业务处理类
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
	 * @param fundRequest
	 * @return
	 */
	public FundTransferInfoDO initFundTransRecord (FundRequest fundRequest) {
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
	
	
	public <T> Object getTransferReord(FundTransTypeEnum fundTransType,Long orderId,String fundStatus){
		Map<String,Object> map = new HashMap<String,Object>();
		if(fundTransType == FundTransTypeEnum.FUND_IN) {
			map.put("orderId", orderId);
			map.put("fundStatus", fundStatus);
			List<FundTransferInfoDO> list  = fundTransferInfoService.list(map);
			if(list.isEmpty() || list.size() > 1) {
				return null;
			}else {
				return list.get(0);
			}	
		}else if(fundTransType == FundTransTypeEnum.REFUND) {
			map.put("orderId", orderId);
			map.put("fundStatus", fundStatus);
			List<RefundInfoDO> list  = refundInfoService.list(map);
			if(list.isEmpty() || list.size() > 1) {
				return null;
			}else {
				return list.get(0);
			}
		}
		return null;
	}
	
	/**
	 * 根据支付接口生成
	 * @param fundTransfer
	 * @return
	 */
	public String fundParamGenerator(FundTransferInfoDO fundTransfer) {
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
		return paymentParam;
	}
	
	/**
	 * 支付成功的处理方法
	 * @param fundRecord
	 */
	public void fundInSuccessProcess(FundTransferInfoDO fundRecord) {
		fundRecord.setTransStatus(FundEnums.SUCCESS.getStatus());
		fundRecord.setGmtModify(new Date());
		fundTransferInfoService.update(fundRecord);
	}
	
	public void reportToWX(String returnCode,String returnMsg) throws Exception {
		// 向微信通知返回结果
		logger.info("业务处理结束，准备向微信发起报告....");
		Map<String, String> reqData = new HashMap<String, String>();
		reqData.put("return_code", returnCode);
		reqData.put("return_msg", returnMsg);
		Map<String, String> reportResult = wxpay.report(reqData);
		logger.info("报告完成....报告结果:{}", WXPayUtil.mapToXml(reportResult));

	}
	
	
	
}
