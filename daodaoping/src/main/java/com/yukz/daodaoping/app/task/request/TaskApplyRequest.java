package com.yukz.daodaoping.app.task.request;

import java.util.Date;

/**
 * 任务申请请求类
 * @author micezhao
 *
 */
public class TaskApplyRequest {
	
	private Long taskTypeId;
	
	private String assistantType;
	
	private String command;
	
	private int taskNum;
	
	private int completedNum;
	
	private String taskResult;
	
	private String taskStatus;
	
	private Date startTime;
	
	private Date expireTime;
	
	private boolean isAppointment;

	public Long getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(Long taskTypeId) {
		this.taskTypeId = taskTypeId;
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

	public int getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(int taskNum) {
		this.taskNum = taskNum;
	}

	public int getCompletedNum() {
		return completedNum;
	}

	public void setCompletedNum(int completedNum) {
		this.completedNum = completedNum;
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

	public boolean isAppointment() {
		return isAppointment;
	}

	public void setAppointment(boolean isAppointment) {
		this.isAppointment = isAppointment;
	}

	
	
	
}
