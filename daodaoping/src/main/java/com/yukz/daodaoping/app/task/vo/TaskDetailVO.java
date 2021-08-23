package com.yukz.daodaoping.app.task.vo;

import java.util.Date;

public class TaskDetailVO {

	private Long id;
	// 用户编号
	private Long userId;
	// 任务类型id
	private Long taskTypeId;

	// 任务类型说明
	private String taskTypeDesc;
	// 助力方式
	private String taskType;

	// 助力方式
	private String assistantType;
	// 任务指令
	private String command;
	// 任务数量
	private Integer taskNumber;
	// 已完成数量
	private Integer completedNumber;
	// 任务结果 uncompleted/completed
	private String taskResult;
	// 任务状态 suspend/wait/pending/end/cancel
	private String taskStatus;
	// 任务开启时间
	private Date startTime;
	// 任务过期时间
	private Date expireTime;
	// 机构编号
	private Long agentId;
	// 创建时间
	private Date gmtCreate;
	// 修改时间
	private Date gmtModify;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getTaskTypeId() {
		return taskTypeId;
	}
	public void setTaskTypeId(Long taskTypeId) {
		this.taskTypeId = taskTypeId;
	}
	public String getTaskTypeDesc() {
		return taskTypeDesc;
	}
	public void setTaskTypeDesc(String taskTypeDesc) {
		this.taskTypeDesc = taskTypeDesc;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getAssistantType() {
		return assistantType;
	}
	public void setAssistantType(String assistantType) {
		this.assistantType = assistantType;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public Integer getTaskNumber() {
		return taskNumber;
	}
	public void setTaskNumber(Integer taskNumber) {
		this.taskNumber = taskNumber;
	}
	public Integer getCompletedNumber() {
		return completedNumber;
	}
	public void setCompletedNumber(Integer completedNumber) {
		this.completedNumber = completedNumber;
	}
	public String getTaskResult() {
		return taskResult;
	}
	public void setTaskResult(String taskResult) {
		this.taskResult = taskResult;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
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
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModify() {
		return gmtModify;
	}
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}
	public TaskDetailVO(Long id, Long userId, Long taskTypeId, String taskTypeDesc, String taskType,
			String assistantType, String command, Integer taskNumber, Integer completedNumber, String taskResult,
			String taskStatus, Date startTime, Date expireTime, Long agentId, Date gmtCreate, Date gmtModify) {
		super();
		this.id = id;
		this.userId = userId;
		this.taskTypeId = taskTypeId;
		this.taskTypeDesc = taskTypeDesc;
		this.taskType = taskType;
		this.assistantType = assistantType;
		this.command = command;
		this.taskNumber = taskNumber;
		this.completedNumber = completedNumber;
		this.taskResult = taskResult;
		this.taskStatus = taskStatus;
		this.startTime = startTime;
		this.expireTime = expireTime;
		this.agentId = agentId;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
	}
	public TaskDetailVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
