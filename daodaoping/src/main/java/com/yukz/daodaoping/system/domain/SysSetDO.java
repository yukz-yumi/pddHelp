package com.yukz.daodaoping.system.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 系统设置表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-18 15:55:04
 */
public class SysSetDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//设置key
	private String setKey;
	//设置value
	private String setValue;
	//机构编号
	private Long agentId;
	//平台编码
	private String platform;
	//任务类型
	private String setType;
	//扩展1
	private String ext1;
	//扩展2
	private String ext2;
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
	 * 设置：设置key
	 */
	public void setSetKey(String setKey) {
		this.setKey = setKey;
	}
	/**
	 * 获取：设置key
	 */
	public String getSetKey() {
		return setKey;
	}
	/**
	 * 设置：设置value
	 */
	public void setSetValue(String setValue) {
		this.setValue = setValue;
	}
	/**
	 * 获取：设置value
	 */
	public String getSetValue() {
		return setValue;
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
	 * 设置：平台编码
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	/**
	 * 获取：平台编码
	 */
	public String getPlatform() {
		return platform;
	}
	/**
	 * 设置：任务类型
	 */
	public void setSetType(String setType) {
		this.setType = setType;
	}
	/**
	 * 获取：任务类型
	 */
	public String getSetType() {
		return setType;
	}
	/**
	 * 设置：扩展1
	 */
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	/**
	 * 获取：扩展1
	 */
	public String getExt1() {
		return ext1;
	}
	/**
	 * 设置：扩展2
	 */
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	/**
	 * 获取：扩展2
	 */
	public String getExt2() {
		return ext2;
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
