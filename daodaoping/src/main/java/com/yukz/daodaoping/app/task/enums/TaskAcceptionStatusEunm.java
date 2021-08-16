package com.yukz.daodaoping.app.task.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public enum TaskAcceptionStatusEunm {
	
	PENDING("pending","进行中"),
	EXPIRED("expired","已过期"),
	END("end","任务已完成");
	
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

	private TaskAcceptionStatusEunm(String status, String desc) {
		this.status = status;
		this.desc = desc;
	}
	
	public static TaskAcceptionStatusEunm getByStatus(String status) {
		if(StringUtils.isBlank(status)) {
			return null;
		}else {
			for (TaskAcceptionStatusEunm item : TaskAcceptionStatusEunm.values() ) {
				if(StringUtils.equals(item.getStatus(), status)) {
					return item;
				}
			}
		}
		return null;
	}
	
	public static List<Map<String,String>> toList(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for (TaskAcceptionStatusEunm item : TaskAcceptionStatusEunm.values()) {
			Map<String,String> map = new HashMap<String, String>();
			map.put("status", item.getStatus());
			map.put("desc", item.getDesc());
			list.add(map);
		}
		return list;
	}
	
	
}
