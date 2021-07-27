package com.yukz.daodaoping.feedback.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 用户反馈记录
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 08:44:17
 */
public class FeedbackInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//用户id
	private Long userId;
	//用户openid
	private Long openId;
	//邮箱地址
	private String eMail;
	//反馈问题类型
	private String feedbackType;
	//反馈问题类型
	private String feedbackDesc;
	//图片1
	private String feedbackImgUrl1;
	//图片2
	private String feedbackImgUrl2;
	//图片3
	private String feedbackImgUrl3;
	//图片4
	private String feedbackImgUrl4;
	//图片5
	private String feedbackImgUrl5;
	//处理人id
	private Long workerId;
	//处理结果
	private String handleResule;
	//反馈时间
	private Date feedbackTime;
	//处理时间
	private Date handleTime;
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
	public void setOpenId(Long openId) {
		this.openId = openId;
	}
	/**
	 * 获取：用户openid
	 */
	public Long getOpenId() {
		return openId;
	}
	/**
	 * 设置：邮箱地址
	 */
	public void setEMail(String eMail) {
		this.eMail = eMail;
	}
	/**
	 * 获取：邮箱地址
	 */
	public String getEMail() {
		return eMail;
	}
	/**
	 * 设置：反馈问题类型
	 */
	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}
	/**
	 * 获取：反馈问题类型
	 */
	public String getFeedbackType() {
		return feedbackType;
	}
	/**
	 * 设置：反馈问题类型
	 */
	public void setFeedbackDesc(String feedbackDesc) {
		this.feedbackDesc = feedbackDesc;
	}
	/**
	 * 获取：反馈问题类型
	 */
	public String getFeedbackDesc() {
		return feedbackDesc;
	}
	/**
	 * 设置：图片1
	 */
	public void setFeedbackImgUrl1(String feedbackImgUrl1) {
		this.feedbackImgUrl1 = feedbackImgUrl1;
	}
	/**
	 * 获取：图片1
	 */
	public String getFeedbackImgUrl1() {
		return feedbackImgUrl1;
	}
	/**
	 * 设置：图片2
	 */
	public void setFeedbackImgUrl2(String feedbackImgUrl2) {
		this.feedbackImgUrl2 = feedbackImgUrl2;
	}
	/**
	 * 获取：图片2
	 */
	public String getFeedbackImgUrl2() {
		return feedbackImgUrl2;
	}
	/**
	 * 设置：图片3
	 */
	public void setFeedbackImgUrl3(String feedbackImgUrl3) {
		this.feedbackImgUrl3 = feedbackImgUrl3;
	}
	/**
	 * 获取：图片3
	 */
	public String getFeedbackImgUrl3() {
		return feedbackImgUrl3;
	}
	/**
	 * 设置：图片4
	 */
	public void setFeedbackImgUrl4(String feedbackImgUrl4) {
		this.feedbackImgUrl4 = feedbackImgUrl4;
	}
	/**
	 * 获取：图片4
	 */
	public String getFeedbackImgUrl4() {
		return feedbackImgUrl4;
	}
	/**
	 * 设置：图片5
	 */
	public void setFeedbackImgUrl5(String feedbackImgUrl5) {
		this.feedbackImgUrl5 = feedbackImgUrl5;
	}
	/**
	 * 获取：图片5
	 */
	public String getFeedbackImgUrl5() {
		return feedbackImgUrl5;
	}
	/**
	 * 设置：处理人id
	 */
	public void setWorkerId(Long workerId) {
		this.workerId = workerId;
	}
	/**
	 * 获取：处理人id
	 */
	public Long getWorkerId() {
		return workerId;
	}
	/**
	 * 设置：处理结果
	 */
	public void setHandleResule(String handleResule) {
		this.handleResule = handleResule;
	}
	/**
	 * 获取：处理结果
	 */
	public String getHandleResule() {
		return handleResule;
	}
	/**
	 * 设置：反馈时间
	 */
	public void setFeedbackTime(Date feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	/**
	 * 获取：反馈时间
	 */
	public Date getFeedbackTime() {
		return feedbackTime;
	}
	/**
	 * 设置：处理时间
	 */
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
	/**
	 * 获取：处理时间
	 */
	public Date getHandleTime() {
		return handleTime;
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
