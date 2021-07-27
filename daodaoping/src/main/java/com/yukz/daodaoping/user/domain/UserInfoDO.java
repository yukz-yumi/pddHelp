package com.yukz.daodaoping.user.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 用户信息表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 08:07:22
 */
public class UserInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//用户编号
	private Long userId;
	//用户openid
	private String openId;
	//用户微信昵称
	private String nickName;
	//用户头像
	private String headImgUrl;
	//用户当前积分
	private Long scores;
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
	 * 设置：用户openid
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	/**
	 * 获取：用户openid
	 */
	public String getOpenId() {
		return openId;
	}
	/**
	 * 设置：用户微信昵称
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * 获取：用户微信昵称
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * 设置：用户头像
	 */
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	/**
	 * 获取：用户头像
	 */
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	/**
	 * 设置：用户当前积分
	 */
	public void setScores(Long scores) {
		this.scores = scores;
	}
	/**
	 * 获取：用户当前积分
	 */
	public Long getScores() {
		return scores;
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
