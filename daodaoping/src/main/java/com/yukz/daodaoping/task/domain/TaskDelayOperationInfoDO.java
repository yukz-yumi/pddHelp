package com.yukz.daodaoping.task.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 任务延迟操作信息表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-02 08:33:34
 */
public class TaskDelayOperationInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//用户id
	private Long taskId;
	//延迟次数
	private Integer delayCount;
	//延迟时间
	private Date delayTime;
	//过期时间
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
	 * 设置：用户id
	 */
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	/**
	 * 获取：用户id
	 */
	public Long getTaskId() {
		return taskId;
	}
	/**
	 * 设置：延迟次数
	 */
	public void setDelayCount(Integer delayCount) {
		this.delayCount = delayCount;
	}
	/**
	 * 获取：延迟次数
	 */
	public Integer getDelayCount() {
		return delayCount;
	}
	/**
	 * 设置：延迟时间
	 */
	public void setDelayTime(Date delayTime) {
		this.delayTime = delayTime;
	}
	/**
	 * 获取：延迟时间
	 */
	public Date getDelayTime() {
		return delayTime;
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
