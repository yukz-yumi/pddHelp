package com.yukz.daodaoping.app.task.request;

import java.util.Date;

/**
 * 任务申请请求类
 * @author micezhao
 *
 */
public class TaskRequest {
	
	private Long id;
	
	private Long taskTypeId;
	
	private String assistantType;
	
	private boolean isAppointment;
	
	//如果是多个地址，就用,隔开
	private String certificationUrl;

	private String command; // 抽象字段：link，command，picturelink
	
	private Integer taskNumber;
	//已完成数量
	private Integer completedNumber;
	
	private String taskResult;
	
	private String taskStatus;
	
	private Date startTime;
	
	private Date expireTime;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCertificationUrl() {
		return certificationUrl;
	}

	public void setCertificationUrl(String certificationUrl) {
		this.certificationUrl = certificationUrl;
	}

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

	public boolean isAppointment() {
		return isAppointment;
	}

	public void setAppointment(boolean isAppointment) {
		this.isAppointment = isAppointment;
	}


	public TaskRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	
	
	
}
