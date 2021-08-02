package com.yukz.daodaoping.task.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 任务认领表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-02 09:04:23
 */
public class TaskAcceptInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//任务id
	private Long taskId;
	//用户id
	private Long userId;
	//用户openid
	private String openId;
	//任务凭证
	private String certificationUrl;
	//任务状态 pending:接受执行中/expire:已过期/end:已完成
	private String taskStatus;
	//审核结果 verifying:审核中/unverified:未通过/verified:已审核 
	private String verifyStatus;
	//审核人id
	private Long workerId;
	//是否放款 0:未放款/1:已放款
	private String hasPaid;
	//机构编号
	private Long agentId;
	//任务过期时间
	private Date expireTime;
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
	 * 设置：用户openid
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	/**
	 * 获取：用户openid
	 */
	public String getOpenId() {
		return openId;
	}
	/**
	 * 设置：任务凭证
	 */
	public void setCertificationUrl(String certificationUrl) {
		this.certificationUrl = certificationUrl;
	}
	/**
	 * 获取：任务凭证
	 */
	public String getCertificationUrl() {
		return certificationUrl;
	}
	/**
	 * 设置：任务状态 pending:接受执行中/expire:已过期/end:已完成
	 */
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	/**
	 * 获取：任务状态 pending:接受执行中/expire:已过期/end:已完成
	 */
	public String getTaskStatus() {
		return taskStatus;
	}
	/**
	 * 设置：审核结果 verifying:审核中/unverified:未通过/verified:已审核 
	 */
	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	/**
	 * 获取：审核结果 verifying:审核中/unverified:未通过/verified:已审核 
	 */
	public String getVerifyStatus() {
		return verifyStatus;
	}
	/**
	 * 设置：审核人id
	 */
	public void setWorkerId(Long workerId) {
		this.workerId = workerId;
	}
	/**
	 * 获取：审核人id
	 */
	public Long getWorkerId() {
		return workerId;
	}
	/**
	 * 设置：是否放款 0:未放款/1:已放款
	 */
	public void setHasPaid(String hasPaid) {
		this.hasPaid = hasPaid;
	}
	/**
	 * 获取：是否放款 0:未放款/1:已放款
	 */
	public String getHasPaid() {
		return hasPaid;
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
