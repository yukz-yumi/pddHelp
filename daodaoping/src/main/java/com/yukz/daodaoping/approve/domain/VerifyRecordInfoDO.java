package com.yukz.daodaoping.approve.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 任务审核记录表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 08:09:51
 */
public class VerifyRecordInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//任务认领id
	private Long taskAcceptId;
	//任务id
	private Long taskId;
	//任务id
	private Long workerId;
	//审核动作
	private String operation;
	//过期时间
	private Date expireTime;
	//打回原因
	private String invalidateReason;
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
	 * 设置：任务认领id
	 */
	public void setTaskAcceptId(Long taskAcceptId) {
		this.taskAcceptId = taskAcceptId;
	}
	/**
	 * 获取：任务认领id
	 */
	public Long getTaskAcceptId() {
		return taskAcceptId;
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
	 * 设置：任务id
	 */
	public void setWorkerId(Long workerId) {
		this.workerId = workerId;
	}
	/**
	 * 获取：任务id
	 */
	public Long getWorkerId() {
		return workerId;
	}
	/**
	 * 设置：审核动作
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
	/**
	 * 获取：审核动作
	 */
	public String getOperation() {
		return operation;
	}
	/**
	 * 设置：过期时间
	 */
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	/**
	 * 获取：过期时间
	 */
	public Date getExpireTime() {
		return expireTime;
	}
	/**
	 * 设置：打回原因
	 */
	public void setInvalidateReason(String invalidateReason) {
		this.invalidateReason = invalidateReason;
	}
	/**
	 * 获取：打回原因
	 */
	public String getInvalidateReason() {
		return invalidateReason;
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
