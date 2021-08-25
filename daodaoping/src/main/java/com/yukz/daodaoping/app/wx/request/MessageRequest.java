package com.yukz.daodaoping.app.wx.request;

import java.util.Map;

public class MessageRequest {
	
	private Long userId;
	
	private String openId;
	
	private Long agentId;
	
	private String templateId;
	
	private String url;
	
//	private String miniprogram;
	
	private String title;
	
	private String amount;
	
	private String detail;
	
	private String memo;
	
	private Map<String,String> data;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public MessageRequest(Long userId, String openId, Long agentId, String templateId, String url, String title,
			String amount, String detail, String memo, Map<String, String> data) {
		super();
		this.userId = userId;
		this.openId = openId;
		this.agentId = agentId;
		this.templateId = templateId;
		this.url = url;
		this.title = title;
		this.amount = amount;
		this.detail = detail;
		this.memo = memo;
		this.data = data;
	}

	public MessageRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
