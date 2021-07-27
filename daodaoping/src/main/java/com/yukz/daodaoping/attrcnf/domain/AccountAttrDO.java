package com.yukz.daodaoping.attrcnf.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 账号属性配置表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 07:52:47
 */
public class AccountAttrDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//用户id
	private Long exAccountId;
	//属性名
	private String attrKey;
	//属性值
	private String attrValue;
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
	public void setExAccountId(Long exAccountId) {
		this.exAccountId = exAccountId;
	}
	/**
	 * 获取：用户id
	 */
	public Long getExAccountId() {
		return exAccountId;
	}
	/**
	 * 设置：属性名
	 */
	public void setAttrKey(String attrKey) {
		this.attrKey = attrKey;
	}
	/**
	 * 获取：属性名
	 */
	public String getAttrKey() {
		return attrKey;
	}
	/**
	 * 设置：属性值
	 */
	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	/**
	 * 获取：属性值
	 */
	public String getAttrValue() {
		return attrValue;
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
