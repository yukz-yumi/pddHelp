package com.yukz.daodaoping.system.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FileCategoryEnum {

	TASKTYPE("taskType","任务类型"),
	EX_ACCOUNT("exAccount","外部账号"),
	TASK_APPLY("taskApply","任务申请"),
	TASK_ACCEPT("taskAccept", "任务认领");

	private String code;

	private String desc;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private FileCategoryEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static FileCategoryEnum getEnumByCode(String code) {
		if(StringUtils.isBlank(code)) {
			return null;
		}else {
			for(FileCategoryEnum item : FileCategoryEnum.values()) {
				if(StringUtils.equals(item.getCode(), code)) {
					return item;
				}
			}
		}
		return null;
	}

	public static Map<String, String> toMap() {
		Map<String, String> enumDataMap = new HashMap<String, String>();
		for (FileCategoryEnum tradeType : values()) {
			enumDataMap.put(tradeType.getCode(), tradeType.getDesc());
		}
		return enumDataMap;
	}

	// 普通方法
	public static String getDesc(String code) {
		for (FileCategoryEnum c : FileCategoryEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.desc;
			}
		}
		return null;
	}

	// 获取所有集合
	public static List<Map<String,String>> toList() {
		List<Map<String,String>> list = new ArrayList<>();
		for (FileCategoryEnum c : FileCategoryEnum.values()) {
			Map<String,String> map = new HashMap<>();
			map.put("code",c.getCode());
			map.put("name",c.getDesc());
			list.add(map);
		}
		return list;
	}
}
