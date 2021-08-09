package com.yukz.daodaoping.app.task.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yukz.daodaoping.app.common.AssisantTypeEnum;

public enum TaskStatusEnum {
	
	SUSPEND("suspend","挂起"),
	WAIT("wait","等待"),
	PENDING("pending","进行中"),
	END("end","已结束"),
	CANCEL("cancel","已取消");
	
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

	private TaskStatusEnum(String status, String desc) {
		this.status = status;
		this.desc = desc;
	}
	
	public static TaskStatusEnum getEnumByStatus(String status) {
		if(StringUtils.isBlank(status)) {
			return null;
		}else {
			for (TaskStatusEnum item : TaskStatusEnum.values() ) {
				if(StringUtils.equals(item.getStatus(), status)) {
					return item;
				}
			}
		}
		return null;
	}
	
	public static List<Map<String,String>> toList(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for (TaskStatusEnum item : TaskStatusEnum.values()) {
			Map<String,String> map = new HashMap<String, String>();
			map.put("status", item.getStatus());
			map.put("desc", item.getDesc());
			list.add(map);
		}
		return list;
	}
	
}
