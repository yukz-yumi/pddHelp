package com.yukz.daodaoping.app.fund;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yukz.daodaoping.app.fund.enums.FundEnums;
import com.yukz.daodaoping.app.fund.enums.FundTransType;
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
	
	@Autowired
	private FundTransferInfoService fundTransferInfoService;
	
	@Autowired
	private RefundInfoService refundInfoService;
	
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
	
	public RefundInfoDO initRefundRecord(FundRequest fundRequest) {
		RefundInfoDO refundRecord = new RefundInfoDO();
		Long orderId = fundRequest.getOrderId();
		Long taskId = fundRequest.getTaskId();
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
	
	
	
	
	
}
