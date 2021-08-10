package com.yukz.daodaoping.discount.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 任务折扣表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-10 13:20:03
 */
public class TaskDiscountInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//活动编号不对客户端展示
	private Long discountConfigId;
	//任务编号
	private Long taskId;
	//活动编号不对客户端展示
	private Integer discountRate;
	//是否可用
	private String allowed;
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
	 * 设置：活动编号不对客户端展示
	 */
	public void setDiscountConfigId(Long discountConfigId) {
		this.discountConfigId = discountConfigId;
	}
	/**
	 * 获取：活动编号不对客户端展示
	 */
	public Long getDiscountConfigId() {
		return discountConfigId;
	}
	/**
	 * 设置：任务编号
	 */
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	/**
	 * 获取：任务编号
	 */
	public Long getTaskId() {
		return taskId;
	}
	/**
	 * 设置：活动编号不对客户端展示
	 */
	public void setDiscountRate(Integer discountRate) {
		this.discountRate = discountRate;
	}
	/**
	 * 获取：活动编号不对客户端展示
	 */
	public Integer getDiscountRate() {
		return discountRate;
	}
	/**
	 * 设置：是否可用
	 */
	public void setAllowed(String allowed) {
		this.allowed = allowed;
	}
	/**
	 * 获取：是否可用
	 */
	public String getAllowed() {
		return allowed;
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
