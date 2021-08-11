package com.yukz.daodaoping.app.fund.enums;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;

public enum FundEnums {
	
	WAIT("wait","支付中"),
	SUCCESS("success","支付成功"),
	FAIL("fail","支付失败");
	
	private String status;
	private String desc;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	private FundEnums(String status, String desc) {
		this.status = status;
		this.desc = desc;
	}
	
	public static FundEnums getEnumByStatus(String status) {
		if(StringUtils.isBlank(status)) {
			return null;
		}else {
			for (FundEnums item : FundEnums.values()) {
				if(StringUtils.equals(item.getDesc(), status)) {
					return item;
				}
			}
		}
		return null;
	}
	
	
}
