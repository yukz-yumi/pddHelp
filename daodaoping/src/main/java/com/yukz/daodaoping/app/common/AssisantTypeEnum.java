package com.yukz.daodaoping.app.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum AssisantTypeEnum {
	COMMAND("command","口令"),
	PICTURE("picture","图片"),
	LINK("link","链接分享");
	
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

	private AssisantTypeEnum(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	
	public static List<Map<String,String>> toList(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for (AssisantTypeEnum item : AssisantTypeEnum.values()) {
			Map<String,String> map = new HashMap<String, String>();
			map.put("type", item.getType());
			map.put("desc", item.getDesc());
			list.add(map);
		}
		return list;
	}
	
}
