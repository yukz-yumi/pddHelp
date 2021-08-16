package com.yukz.daodaoping.system.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ImgTypeEnum {

	JPG("jpg","jpg文件"),
	JPEG("jpeg","jpeg文件"),
	PNG("png","png文件"),
	BMP("bmp","bmp文件"),
	GIF("gif", "gif文件");

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

	private ImgTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static ImgTypeEnum getEnumByCode(String code) {
		if(StringUtils.isBlank(code)) {
			return null;
		}else {
			for(ImgTypeEnum item : ImgTypeEnum.values()) {
				if(StringUtils.equals(item.getCode(), code)) {
					return item;
				}
			}
		}
		return null;
	}

	public static Map<String, String> toMap() {
		Map<String, String> enumDataMap = new HashMap<String, String>();
		for (ImgTypeEnum tradeType : values()) {
			enumDataMap.put(tradeType.getCode(), tradeType.getDesc());
		}
		return enumDataMap;
	}

	// 普通方法
	public static String getDesc(String code) {
		for (ImgTypeEnum c : ImgTypeEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.desc;
			}
		}
		return null;
	}

	// 获取所有集合
	public static List<Map<String,String>> toList() {
		List<Map<String,String>> list = new ArrayList<>();
		for (ImgTypeEnum c : ImgTypeEnum.values()) {
			Map<String,String> map = new HashMap<>();
			map.put("code",c.getCode());
			map.put("name",c.getDesc());
			list.add(map);
		}
		return list;
	}
}
