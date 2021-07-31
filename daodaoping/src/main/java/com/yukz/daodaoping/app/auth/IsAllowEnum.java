package com.yukz.daodaoping.app.auth;

import org.apache.commons.lang3.StringUtils;

public enum IsAllowEnum {
	
	YES("yes","允许使用"),
	NO("no","不允许使用");
	
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

	private IsAllowEnum(String status, String desc) {
		this.status = status;
		this.desc = desc;
	}
	
	public static IsAllowEnum getEnumByStatus(String status) {
		if(StringUtils.isBlank(status)) {
			return null;
		}else {
			for (IsAllowEnum item : IsAllowEnum.values()) {
				if(StringUtils.equals(item.getStatus(), status)) {
					return item;
				}
			}
		}
		return null;
	}
	
}
