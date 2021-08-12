package com.yukz.daodaoping.app.fund.enums;

import org.apache.commons.lang3.StringUtils;

public enum FundTransTypeEnum {
	
	FUND_IN("fund_in","入金"),
	FUND_OUT("fund_out","出金"),
	REFUND("refund","退费");
	
	private String type;
	
	private String desc;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private FundTransTypeEnum(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	
	public static FundTransTypeEnum getByType(String type) {
		if(StringUtils.isBlank(type)) {
			return null;
		}else {
			for (FundTransTypeEnum item : FundTransTypeEnum.values()) {
				if(StringUtils.equals(item.getDesc(), type)) {
					return item;
				}
			}
		}
		return null;
	}
	
	
	
}
