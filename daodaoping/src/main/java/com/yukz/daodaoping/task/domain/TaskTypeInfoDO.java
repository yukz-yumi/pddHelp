package com.yukz.daodaoping.task.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 任务类型表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-01 11:29:03
 */
public class TaskTypeInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//平台类型
	private String platform;
	//任务类型说明
	private String taskTypeDesc;
	//助力方式
	private String taskType;
	//任务图片
	private String taskImg;
	//是否启用: yes/no
	private String allowed;
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
	 * 设置：平台类型
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	/**
	 * 获取：平台类型
	 */
	public String getPlatform() {
		return platform;
	}
	/**
	 * 设置：任务类型说明
	 */
	public void setTaskTypeDesc(String taskTypeDesc) {
		this.taskTypeDesc = taskTypeDesc;
	}
	/**
	 * 获取：任务类型说明
	 */
	public String getTaskTypeDesc() {
		return taskTypeDesc;
	}
	/**
	 * 设置：助力方式
	 */
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	/**
	 * 获取：助力方式
	 */
	public String getTaskType() {
		return taskType;
	}
	/**
	 * 设置：任务图片
	 */
	public void setTaskImg(String taskImg) {
		this.taskImg = taskImg;
	}
	/**
	 * 获取：任务图片
	 */
	public String getTaskImg() {
		return taskImg;
	}
	/**
	 * 设置：是否启用: yes/no
	 */
	public void setAllowed(String allowed) {
		this.allowed = allowed;
	}
	/**
	 * 获取：是否启用: yes/no
	 */
	public String getAllowed() {
		return allowed;
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
