package com.yukz.daodaoping.app.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum TimeScopeEnum {
	
	WEEK("week","一周内"),
	MONTH("month","一个月内"),
	QUARTER("3month","三个月内"),
	HALFYEAR("halfyear","半年内");
	
	
	private String scope;
	private String desc;
	
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private TimeScopeEnum(String scope, String desc) {
		this.scope = scope;
		this.desc = desc;
	}
	
	public static List<Map<String,String>> toList(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for (TimeScopeEnum item : TimeScopeEnum.values()) {
			Map<String,String> map = new HashMap<String, String>();
			map.put("scope", item.getScope());
			map.put("desc", item.getDesc());
			list.add(map);
		}
		return list;
	}
	
}
