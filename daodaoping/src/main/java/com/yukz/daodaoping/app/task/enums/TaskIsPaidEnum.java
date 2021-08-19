package com.yukz.daodaoping.app.task.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yukz.daodaoping.app.fund.enums.FundBizEnum;

/**
 * 任务奖金是否支付
 * 
 * @author micezhao
 *
 */
public enum TaskIsPaidEnum {
	
	UNPAID("0","unpaid", "未放款"),
	PAID("1","paid", "已放款");
	
	private String code;
	
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

	
	
	private TaskIsPaidEnum(String code, String status, String desc) {
		this.code = code;
		this.status = status;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static TaskIsPaidEnum getByStatus(String status) {
		if(StringUtils.isBlank(status)) {
			return null;
		}else {
			for (TaskIsPaidEnum item : TaskIsPaidEnum.values()) {
				if(StringUtils.equals(item.getStatus(), status)) {
					return item;
				}
			}
		}
		return null;
	}
	
	public static TaskIsPaidEnum getByCode(String code) {
		if(StringUtils.isBlank(code)) {
			return null;
		}else {
			for (TaskIsPaidEnum item : TaskIsPaidEnum.values()) {
				if(StringUtils.equals(item.getCode(), code)) {
					return item;
				}
			}
		}
		return null;
	}
	
	public static List<Map<String,String>> toList(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for (TaskIsPaidEnum item : TaskIsPaidEnum.values()) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("code", item.code);
			map.put("type", item.getStatus());
			map.put("desc", item.getDesc());
			list.add(map);
		}
		return list;
	}

}
