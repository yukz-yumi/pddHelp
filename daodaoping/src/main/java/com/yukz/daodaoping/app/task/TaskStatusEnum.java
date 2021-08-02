package com.yukz.daodaoping.app.task;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;

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
	
}
