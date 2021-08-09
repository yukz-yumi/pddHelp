package com.yukz.daodaoping.app.enums;

import java.util.Iterator;

import com.yukz.daodaoping.task.enums.PlatformEnum;
import org.apache.commons.lang3.StringUtils;

public enum UserStatusEnum {
	
	UNBIND("unbind","未绑定外部账号"),
	NORMAL("normal","正常"),
	FORBIDDEN("forbidden","禁用");
	
	private String userStatus;
	
	private String desc;
	
	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}

	private UserStatusEnum(String userStatus, String desc) {
		this.userStatus = userStatus;
		this.desc = desc;
	}
	
	public static UserStatusEnum getEnumByStatus(String status) {
		if(StringUtils.isBlank(status)) {
			return null;
		}else {
			for (UserStatusEnum item : UserStatusEnum.values()) {
				if(StringUtils.equals(item.getUserStatus(), status)) {
					return item;
				}
			}
		}
		return null;
	}

	// 普通方法
	public static String getDesc(String code) {
		for (UserStatusEnum c : UserStatusEnum.values()) {
			if (c.getUserStatus().equals(code)) {
				return c.desc;
			}
		}
		return null;
	}
}
