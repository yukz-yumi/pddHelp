package com.yukz.daodaoping.task.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 任务申请表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 08:06:02
 */
public class TaskApplyInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//任务编号
	private String taskId;
	//任务类型id
	private String taskTypeId;
	//助力方式
	private String assistantType;
	//任务指令
	private String command;
	//任务数量
	private Integer taskNumber;
	//已完成数量
	private Integer completedNumeber;
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
	 * 设置：任务编号
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	/**
	 * 获取：任务编号
	 */
	public String getTaskId() {
		return taskId;
	}
	/**
	 * 设置：任务类型id
	 */
	public void setTaskTypeId(String taskTypeId) {
		this.taskTypeId = taskTypeId;
	}
	/**
	 * 获取：任务类型id
	 */
	public String getTaskTypeId() {
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
	public void setCompletedNumeber(Integer completedNumeber) {
		this.completedNumeber = completedNumeber;
	}
	/**
	 * 获取：已完成数量
	 */
	public Integer getCompletedNumeber() {
		return completedNumeber;
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
}
