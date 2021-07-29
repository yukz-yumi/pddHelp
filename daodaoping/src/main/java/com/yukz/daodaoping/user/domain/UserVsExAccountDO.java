package com.yukz.daodaoping.user.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 用户与外部账号映射表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-29 11:09:24
 */
public class UserVsExAccountDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//用户编号
	private Long userId;
	//账号类型
	private String accountType;
	//账号
	private String account;
	//用户是否启用: yes/no
	private String allowed;
	//系统为当前账号设定的状态 available/forbidden
	private String accountStatus;
	//账号截图图片地址
	private String accountImg;
	//用户等级
	private String userGrade;
	//用户联系方式
	private String mobile;
	//用户状态:unverified 未认证/ verified 已认证/ forbidden 已停用
	private String userStatus;
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
	 * 设置：用户编号
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户编号
	 */
	public Long getUserId() {
		return userId;
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
	 * 设置：账号
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取：账号
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置：用户是否启用: yes/no
	 */
	public void setAllowed(String allowed) {
		this.allowed = allowed;
	}
	/**
	 * 获取：用户是否启用: yes/no
	 */
	public String getAllowed() {
		return allowed;
	}
	/**
	 * 设置：系统为当前账号设定的状态 available/forbidden
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	/**
	 * 获取：系统为当前账号设定的状态 available/forbidden
	 */
	public String getAccountStatus() {
		return accountStatus;
	}
	/**
	 * 设置：账号截图图片地址
	 */
	public void setAccountImg(String accountImg) {
		this.accountImg = accountImg;
	}
	/**
	 * 获取：账号截图图片地址
	 */
	public String getAccountImg() {
		return accountImg;
	}
	/**
	 * 设置：用户等级
	 */
	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}
	/**
	 * 获取：用户等级
	 */
	public String getUserGrade() {
		return userGrade;
	}
	/**
	 * 设置：用户联系方式
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：用户联系方式
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：用户状态:unverified 未认证/ verified 已认证/ forbidden 已停用
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	/**
	 * 获取：用户状态:unverified 未认证/ verified 已认证/ forbidden 已停用
	 */
	public String getUserStatus() {
		return userStatus;
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
