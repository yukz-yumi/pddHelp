package com.yukz.daodaoping.app.task.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public enum TaskVerifyStatusEnum {
	
	UNVERIFIED("unverified","待审核"),
	VERIFIED("verified","已审核"),
	REJECTED("rejected","被驳回");
	
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
	private TaskVerifyStatusEnum(String status, String desc) {
		this.status = status;
		this.desc = desc;
	}
	
	public static TaskVerifyStatusEnum getByStatus(String status) {
		if(StringUtils.isBlank(status)) {
			return null;
		}else {
			for (TaskVerifyStatusEnum item : TaskVerifyStatusEnum.values() ) {
				if(StringUtils.equals(item.getStatus(), status)) {
					return item;
				}
			}
		}
		return null;
	}
	
	public static List<Map<String,String>> toList(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for (TaskVerifyStatusEnum item : TaskVerifyStatusEnum.values()) {
			Map<String,String> map = new HashMap<String, String>();
			map.put("status", item.getStatus());
			map.put("desc", item.getDesc());
			list.add(map);
		}
		return list;
	}
	
}
