package com.yukz.daodaoping.task.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum PlatformEnum {

	PDD("pdd","拼多多"),
	DOUYIN("douyin","抖音"),
	TAOBAO("taobao","淘宝");

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

	private PlatformEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static PlatformEnum getEnumByCode(String code) {
		if(StringUtils.isBlank(code)) {
			return null;
		}else {
			for(PlatformEnum item : PlatformEnum.values()) {
				if(StringUtils.equals(item.getCode(), code)) {
					return item;
				}
			}
		}
		return null;
	}

	public static Map<String, String> toMap() {
		Map<String, String> enumDataMap = new HashMap<String, String>();
		for (PlatformEnum tradeType : values()) {
			enumDataMap.put(tradeType.getCode(), tradeType.getDesc());
		}
		return enumDataMap;
	}

	// 普通方法
	public static String getDesc(String code) {
		for (PlatformEnum c : PlatformEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.desc;
			}
		}
		return null;
	}

	// 获取所有集合
	public static List<Map<String,String>> toList(String code) {
		List<Map<String,String>> list = new ArrayList<>();
		for (PlatformEnum c : PlatformEnum.values()) {
			Map<String,String> map = new HashMap<>();
			map.put("code",c.getCode());
			map.put("name",c.getDesc());
			list.add(map);
		}
		return list;
	}
}
