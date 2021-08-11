package com.yukz.daodaoping.app.fund;

public class FundRequest {
	// 订单编号
	private Long orderId;
	
	private Long taskId;
	
	// 用户id
	private Long userId;
	// 机构编号
	private Long agentId;
	// 用户openid
	private String openId;
	// 业务类型
	private String bizType;
	// 流水类型
	private String transType;
	// 交易金额
	private Long fundAmount;
	// 交易状态
	private String transStatus;
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getAgentId() {
		return agentId;
	}
	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public Long getFundAmount() {
		return fundAmount;
	}
	public void setFundAmount(Long fundAmount) {
		this.fundAmount = fundAmount;
	}
	public String getTransStatus() {
		return transStatus;
	}
	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public FundRequest(Long orderId, Long taskId, Long userId, Long agentId, String openId, String bizType,
			String transType, Long fundAmount, String transStatus) {
		super();
		this.orderId = orderId;
		this.taskId = taskId;
		this.userId = userId;
		this.agentId = agentId;
		this.openId = openId;
		this.bizType = bizType;
		this.transType = transType;
		this.fundAmount = fundAmount;
		this.transStatus = transStatus;
	}
	
	
	
	
}
