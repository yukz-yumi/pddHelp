package com.yukz.daodaoping.task.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 任务申请表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-09 10:36:56
 */
public class TaskApplyInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//用户编号
	private Long userId;
	//任务类型id
	private Long taskTypeId;
	//助力方式
	private String assistantType;
	//任务指令
	private String command;
	//任务数量
	private Integer taskNumber;
	//已完成数量
	private Integer completedNumber;
	//任务结果 uncompleted/completed
	private String taskResult;
	//任务状态 suspend/wait/pending/end/cancel
	private String taskStatus;
	//任务开启时间
	private Date startTime;
	//任务过期时间
	private Date expireTime;
	//机构编号
	private Long agentId;
	//创建时间
	private Date gmtCreate;
	//修改时间
	private Date gmtModify;

	private Long totalAmount;
	private Long paymentAmount;
	private Long orderId;

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
	 * 设置：用户编号
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户编号
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：任务类型id
	 */
	public void setTaskTypeId(Long taskTypeId) {
		this.taskTypeId = taskTypeId;
	}
	/**
	 * 获取：任务类型id
	 */
	public Long getTaskTypeId() {
		return taskTypeId;
	}
	/**
	 * 设置：助力方式
	 */
	public void setAssistantType(String assistantType) {
		this.assistantType = assistantType;
	}
	/**
	 * 获取：助力方式
	 */
	public String getAssistantType() {
		return assistantType;
	}
	/**
	 * 设置：任务指令
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	/**
	 * 获取：任务指令
	 */
	public String getCommand() {
		return command;
	}
	/**
	 * 设置：任务数量
	 */
	public void setTaskNumber(Integer taskNumber) {
		this.taskNumber = taskNumber;
	}
	/**
	 * 获取：任务数量
	 */
	public Integer getTaskNumber() {
		return taskNumber;
	}
	/**
	 * 设置：已完成数量
	 */
	public void setCompletedNumber(Integer completedNumber) {
		this.completedNumber = completedNumber;
	}
	/**
	 * 获取：已完成数量
	 */
	public Integer getCompletedNumber() {
		return completedNumber;
	}
	/**
	 * 设置：任务结果 uncompleted/completed
	 */
	public void setTaskResult(String taskResult) {
		this.taskResult = taskResult;
	}
	/**
	 * 获取：任务结果 uncompleted/completed
	 */
	public String getTaskResult() {
		return taskResult;
	}
	/**
	 * 设置：任务状态 suspend/wait/pending/end/cancel
	 */
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	/**
	 * 获取：任务状态 suspend/wait/pending/end/cancel
	 */
	public String getTaskStatus() {
		return taskStatus;
	}
	/**
	 * 设置：任务开启时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：任务开启时间
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置：任务过期时间
	 */
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	/**
	 * 获取：任务过期时间
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

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}
