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
	
	UNPAID("unpaid", "未放款"),
	PAID("paid", "已放款");
	
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

	private TaskIsPaidEnum(String status, String desc) {
		this.status = status;
		this.desc = desc;
	}
	
	public static TaskIsPaidEnum getByType(String type) {
		if(StringUtils.isBlank(type)) {
			return null;
		}else {
			for (TaskIsPaidEnum item : TaskIsPaidEnum.values()) {
				if(StringUtils.equals(item.getDesc(), type)) {
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
			map.put("type", item.getStatus());
			map.put("desc", item.getDesc());
			list.add(map);
		}
		return list;
	}

}
