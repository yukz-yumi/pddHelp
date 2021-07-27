package com.yukz.daodaoping.invitation.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 邀请表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 07:56:37
 */
public class InvitationDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//用户id
	private Long userId;
	//用户openid
	private String openId;
	//间接推荐人id
	private Long indirectUserId;
	//直接推荐人id
	private Long directUserId;
	//间接推荐人open_id
	private String directOpenid;
	//直接推荐人open_id
	private String indirectOpenid;
	//直接推荐人昵称
	private String directNickName;
	//间接推荐人昵称
	private String indirectNickName;
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
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
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
	 * 设置：间接推荐人id
	 */
	public void setIndirectUserId(Long indirectUserId) {
		this.indirectUserId = indirectUserId;
	}
	/**
	 * 获取：间接推荐人id
	 */
	public Long getIndirectUserId() {
		return indirectUserId;
	}
	/**
	 * 设置：直接推荐人id
	 */
	public void setDirectUserId(Long directUserId) {
		this.directUserId = directUserId;
	}
	/**
	 * 获取：直接推荐人id
	 */
	public Long getDirectUserId() {
		return directUserId;
	}
	/**
	 * 设置：间接推荐人open_id
	 */
	public void setDirectOpenid(String directOpenid) {
		this.directOpenid = directOpenid;
	}
	/**
	 * 获取：间接推荐人open_id
	 */
	public String getDirectOpenid() {
		return directOpenid;
	}
	/**
	 * 设置：直接推荐人open_id
	 */
	public void setIndirectOpenid(String indirectOpenid) {
		this.indirectOpenid = indirectOpenid;
	}
	/**
	 * 获取：直接推荐人open_id
	 */
	public String getIndirectOpenid() {
		return indirectOpenid;
	}
	/**
	 * 设置：直接推荐人昵称
	 */
	public void setDirectNickName(String directNickName) {
		this.directNickName = directNickName;
	}
	/**
	 * 获取：直接推荐人昵称
	 */
	public String getDirectNickName() {
		return directNickName;
	}
	/**
	 * 设置：间接推荐人昵称
	 */
	public void setIndirectNickName(String indirectNickName) {
		this.indirectNickName = indirectNickName;
	}
	/**
	 * 获取：间接推荐人昵称
	 */
	public String getIndirectNickName() {
		return indirectNickName;
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
