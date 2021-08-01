package com.yukz.daodaoping.app.enums;

import org.apache.commons.lang3.StringUtils;

public enum ExAccountEnum {
	
	AVAILABLE("avaliable","可用"),
	FORBIDDEN("forbidden","禁用"),
	VERIFYING("verifying","审核中");
	
	private String exAccountStatus;
	
	private String desc;

	public String getExAccountStatus() {
		return exAccountStatus;
	}

	public void setExAccountStatus(String exAccountStatus) {
		this.exAccountStatus = exAccountStatus;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private ExAccountEnum(String exAccountStatus, String desc) {
		this.exAccountStatus = exAccountStatus;
		this.desc = desc;
	}
	
	public static ExAccountEnum getEnumByStatus(String status) {
		if(StringUtils.isBlank(status)) {
			return null;
		}else {
			for(ExAccountEnum item : ExAccountEnum.values()) {
				if(StringUtils.equals(item.getExAccountStatus(), status)) {
					return item;
				}
			}
		}
		return null;
	}

}
