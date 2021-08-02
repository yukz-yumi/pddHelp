package com.yukz.daodaoping.app.task.request;

import java.util.Date;

/**
 * 订单请求类
 * @author micezhao
 *
 */
public class OrderRequest {
	
	private Long taskTypeInfoId;
	
	private Long userId;
	
	private Long taskId;
	
	private Long totalAmount;
	
	private Long scoreDeduction;
	
	private double discount;
	
	private Long payAmount;
	
	private String orderStatus;
	
	private Date expireTime;
	
	private Long agentId;

	public Long getTaskTypeInfoId() {
		return taskTypeInfoId;
	}

	public void setTaskTypeInfoId(Long taskTypeInfoId) {
		this.taskTypeInfoId = taskTypeInfoId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getScoreDeduction() {
		return scoreDeduction;
	}

	public void setScoreDeduction(Long scoreDeduction) {
		this.scoreDeduction = scoreDeduction;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
	
	
	
}
