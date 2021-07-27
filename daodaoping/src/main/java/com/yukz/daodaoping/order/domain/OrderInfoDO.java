package com.yukz.daodaoping.order.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 订单表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 08:03:58
 */
public class OrderInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//订单编号
	private Long orderId;
	//用户id
	private Long userId;
	//任务id
	private Long taskId;
	//订单应付
	private Long totalAmount;
	//积分扣减
	private Long scoresDeduction;
	//折扣
	private Integer discount;
	//实际支付订单金额
	private Long paymentAmount;
	//0:未支付/1:支付中/2:已支付/3:已过期/4:已取消
	private String orderStatus;
	//订单支付过期时间
	private Date expireTime;
	//机构编号
	private Long agentId;
	//创建时间
	private Date gmtCreate;
	//修改时间
	private Date gmtModify;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：订单编号
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：订单编号
	 */
	public Long getOrderId() {
		return orderId;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：任务id
	 */
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	/**
	 * 获取：任务id
	 */
	public Long getTaskId() {
		return taskId;
	}
	/**
	 * 设置：订单应付
	 */
	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
	/**
	 * 获取：订单应付
	 */
	public Long getTotalAmount() {
		return totalAmount;
	}
	/**
	 * 设置：积分扣减
	 */
	public void setScoresDeduction(Long scoresDeduction) {
		this.scoresDeduction = scoresDeduction;
	}
	/**
	 * 获取：积分扣减
	 */
	public Long getScoresDeduction() {
		return scoresDeduction;
	}
	/**
	 * 设置：折扣
	 */
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	/**
	 * 获取：折扣
	 */
	public Integer getDiscount() {
		return discount;
	}
	/**
	 * 设置：实际支付订单金额
	 */
	public void setPaymentAmount(Long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	/**
	 * 获取：实际支付订单金额
	 */
	public Long getPaymentAmount() {
		return paymentAmount;
	}
	/**
	 * 设置：0:未支付/1:支付中/2:已支付/3:已过期/4:已取消
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * 获取：0:未支付/1:支付中/2:已支付/3:已过期/4:已取消
	 */
	public String getOrderStatus() {
		return orderStatus;
	}
	/**
	 * 设置：订单支付过期时间
	 */
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	/**
	 * 获取：订单支付过期时间
	 */
	public Date getExpireTime() {
		return expireTime;
	}
	/**
	 * 设置：机构编号
	 */
	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
	/**
	 * 获取：机构编号
	 */
	public Long getAgentId() {
		return agentId;
	}
	/**
	 * 设置：创建时间
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getGmtCreate() {
		return gmtCreate;
	}
	/**
	 * 设置：修改时间
	 */
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getGmtModify() {
		return gmtModify;
	}
}
