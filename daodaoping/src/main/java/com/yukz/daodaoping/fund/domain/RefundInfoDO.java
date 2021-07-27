package com.yukz.daodaoping.fund.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 退款记录表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 07:51:04
 */
public class RefundInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//退费id
	private Long refundId;
	//退费任务id
	private Long taskId;
	//退费金额
	private Long refundAmount;
	//用户id
	private Long userId;
	//用户openid
	private String openId;
	//交易状态:wait:等待/success:成功/fail:失败
	private String refundStatus;
	//失败原因
	private String errMsg;
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
	 * 设置：退费id
	 */
	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}
	/**
	 * 获取：退费id
	 */
	public Long getRefundId() {
		return refundId;
	}
	/**
	 * 设置：退费任务id
	 */
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	/**
	 * 获取：退费任务id
	 */
	public Long getTaskId() {
		return taskId;
	}
	/**
	 * 设置：退费金额
	 */
	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}
	/**
	 * 获取：退费金额
	 */
	public Long getRefundAmount() {
		return refundAmount;
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
	 * 设置：交易状态:wait:等待/success:成功/fail:失败
	 */
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	/**
	 * 获取：交易状态:wait:等待/success:成功/fail:失败
	 */
	public String getRefundStatus() {
		return refundStatus;
	}
	/**
	 * 设置：失败原因
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	/**
	 * 获取：失败原因
	 */
	public String getErrMsg() {
		return errMsg;
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
