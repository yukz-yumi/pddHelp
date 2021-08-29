package com.yukz.daodaoping.task.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum TaskResultEnum {

	UNCOMPLETED("uncompleted","未完成"),
	PARTCOMPLETED("partcompleted","部分完成"),
	COMPLETED("completed","完成");

	private String code;

	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private TaskResultEnum(String code, String desc) {
		this.code = code;
		this.name = desc;
	}
	
	public static TaskResultEnum getEnumByCode(String code) {
		if(StringUtils.isBlank(code)) {
			return null;
		}else {
			for(TaskResultEnum item : TaskResultEnum.values()) {
				if(StringUtils.equals(item.getCode(), code)) {
					return item;
				}
			}
		}
		return null;
	}

	public static Map<String, String> toMap() {
		Map<String, String> enumDataMap = new HashMap<String, String>();
		for (TaskResultEnum tradeType : values()) {
			enumDataMap.put(tradeType.getCode(), tradeType.getName());
		}
		return enumDataMap;
	}

	// 普通方法
	public static String getName(String code) {
		for (TaskResultEnum c : TaskResultEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.name;
			}
		}
		return null;
	}

	// 获取所有集合
	public static List<Map<String,String>> toList() {
		List<Map<String,String>> list = new ArrayList<>();
		for (TaskResultEnum c : TaskResultEnum.values()) {
			Map<String,String> map = new HashMap<>();
			map.put("code",c.getCode());
			map.put("name",c.getName());
			list.add(map);
		}
		return list;
	}
}
