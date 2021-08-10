package com.yukz.daodaoping.discount.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 折扣配置表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-10 13:20:03
 */
public class DiscountConfigDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//活动编号不对客户端展示
	private String discountName;
	//创建时间
	private Date startTime;
	//修改时间
	private Date endTime;
	//upcoming，ongoing，end(此状态可以重新开启)，expired(不能再开始)
	private String discountStatus;
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
	 * 设置：活动编号不对客户端展示
	 */
	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}
	/**
	 * 获取：活动编号不对客户端展示
	 */
	public String getDiscountName() {
		return discountName;
	}
	/**
	 * 设置：创建时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置：修改时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置：upcoming，ongoing，end(此状态可以重新开启)，expired(不能再开始)
	 */
	public void setDiscountStatus(String discountStatus) {
		this.discountStatus = discountStatus;
	}
	/**
	 * 获取：upcoming，ongoing，end(此状态可以重新开启)，expired(不能再开始)
	 */
	public String getDiscountStatus() {
		return discountStatus;
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
