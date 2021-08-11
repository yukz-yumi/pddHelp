package com.yukz.daodaoping.app.fund.enums;

public enum FundTransType {
	
	FUND_IN("fund_in","支付"),
	FUND_OUT("fund_out","提现"),
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

	private FundTransType(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	
	
	
}
