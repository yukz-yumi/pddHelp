package com.yukz.daodaoping.task.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public static Map<String, String> toMap() {
		Map<String, String> enumDataMap = new HashMap<String, String>();
		for (IsAllowEnum c : values()) {
			enumDataMap.put(c.getStatus(), c.getDesc());
		}
		return enumDataMap;
	}

	// 普通方法
	public static String getDesc(String code) {
		for (IsAllowEnum c : IsAllowEnum.values()) {
			if (c.getStatus().equals(code)) {
				return c.desc;
			}
		}
		return null;
	}

	// 获取所有集合
	public static List<Map<String,String>> toList() {
		List<Map<String,String>> list = new ArrayList<>();
		for (IsAllowEnum c : IsAllowEnum.values()) {
			Map<String,String> map = new HashMap<>();
			map.put("status",c.getStatus());
			map.put("desc",c.getDesc());
			list.add(map);
		}
		return list;
	}
}
