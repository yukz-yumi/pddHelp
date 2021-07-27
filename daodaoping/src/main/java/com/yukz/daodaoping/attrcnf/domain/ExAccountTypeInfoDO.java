package com.yukz.daodaoping.attrcnf.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 外部账号类型表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 07:52:47
 */
public class ExAccountTypeInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//账号类型
	private String accountType;
	//平台类型
	private String platform;
	//账号类型说明
	private String typeDesc;
	//账户验证地址
	private String validUrl;
	//机构编号
	private Long agentId;
	//是否启用: yes/no
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
	 * 设置：账号类型
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	/**
	 * 获取：账号类型
	 */
	public String getAccountType() {
		return accountType;
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
	 * 设置：账号类型说明
	 */
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	/**
	 * 获取：账号类型说明
	 */
	public String getTypeDesc() {
		return typeDesc;
	}
	/**
	 * 设置：账户验证地址
	 */
	public void setValidUrl(String validUrl) {
		this.validUrl = validUrl;
	}
	/**
	 * 获取：账户验证地址
	 */
	public String getValidUrl() {
		return validUrl;
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
