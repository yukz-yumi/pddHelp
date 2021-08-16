package com.yukz.daodaoping.app.fund.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FundBizEnum {
	
	FUND_PAY("fund_pay","现金支付"),
	
	BALANCE_PAY("balance_pay","余额支付"),
	
	TASK_TRANSFER("task_transfer","任务奖金"),
	
	COMMISSION("commission","分佣");
	
	private String bizType;
	
	private String desc;

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private FundBizEnum(String bizType, String desc) {
		this.bizType = bizType;
		this.desc = desc;
	}
	
	public List<Map<String,String>> toList(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for (FundBizEnum item : FundBizEnum.values()) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("type", item.getBizType());
			map.put("desc", item.getDesc());
			list.add(map);
		}
		return list;
	}
	
	
	
}
