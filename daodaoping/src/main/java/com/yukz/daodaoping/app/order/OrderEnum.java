package com.yukz.daodaoping.app.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yukz.daodaoping.app.enums.ExAccountEnum;

public enum OrderEnum {
	
	UNPAID("0","unpaid","未支付"),
	PAYING("1","paying","支付中"), 
	PAID("2","paid","已支付"),
	EXPIRED("3","expired","已过期"),
	CANCELED("4","canceled","已取消");
	
	private String code;
	
	private String status;
	
	private String desc;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

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

	private OrderEnum(String code, String status, String desc) {
		this.code = code;
		this.status = status;
		this.desc = desc;
	}
	
	public static List<Map<String,String>> toList(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for (OrderEnum item: OrderEnum.values()) {
			Map<String,String> map = new HashMap<String, String>();
			map.put("code", item.code);
			map.put("status", item.status);
			map.put("desc", item.desc);
			list.add(map);
		}
		return list;
	}
	
	public static OrderEnum getByCode(String code) {
		if(StringUtils.isBlank(code)) {
			return null;
		}else {
			for(OrderEnum item : OrderEnum.values()) {
				if(StringUtils.equals(item.getCode(), code)) {
					return item;
				}
			}
		}
		return null;
	}
	
}
