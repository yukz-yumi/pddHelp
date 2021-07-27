package com.yukz.daodaoping.fund.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 资金流水表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 07:51:04
 */
public class FundTransferInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//订单编号
	private Long orderId;
	//用户id
	private Long userId;
	//用户openid
	private String openId;
	//业务类型
	private String bizType;
	//流水类型
	private String transType;
	//交易金额
	private Long fundAmount;
	//交易状态
	private String transStatus;
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
	 * 设置：订单编号
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：订单编号
	 */
	public Long getOrderId() {
		return orderId;
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
	 * 设置：业务类型
	 */
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	/**
	 * 获取：业务类型
	 */
	public String getBizType() {
		return bizType;
	}
	/**
	 * 设置：流水类型
	 */
	public void setTransType(String transType) {
		this.transType = transType;
	}
	/**
	 * 获取：流水类型
	 */
	public String getTransType() {
		return transType;
	}
	/**
	 * 设置：交易金额
	 */
	public void setFundAmount(Long fundAmount) {
		this.fundAmount = fundAmount;
	}
	/**
	 * 获取：交易金额
	 */
	public Long getFundAmount() {
		return fundAmount;
	}
	/**
	 * 设置：交易状态
	 */
	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}
	/**
	 * 获取：交易状态
	 */
	public String getTransStatus() {
		return transStatus;
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
