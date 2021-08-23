package com.yukz.daodaoping.app.order.vo;

import java.util.Date;

public class OrderDetailVO {
	
	private Long id;
	//订单编号
	private Long orderId;
	//用户id
	private Long userId;
	//任务id
	private Long taskId;
	//订单应付
	private Long totalAmount;
	//积分扣减
	private Long scoresDeduction;
	//折扣
	private Integer discount;
	//实际支付订单金额
	private Long paymentAmount;
	//0:未支付/1:支付中/2:已支付/3:已过期/4:已取消
	private String orderStatus;
	//订单支付过期时间
	private Date expireTime;
	//机构编号
	private Long agentId;
	//创建时间
	private Date gmtCreate;
	//修改时间
	private Date gmtModify;

	private Long taskTypeId;
	
	private String platform;
	//任务类型说明
	private String taskTypeDesc;
	//助力方式
	private String taskType;
	//任务图片
	private String taskImg;
	
	private String assistantType;
	
	private Integer taskNumber;
	//已完成数量
	private Integer completedNumber;
	//任务结果 uncompleted/completed
	private String taskResult;
	//任务状态 suspend/wait/pending/end/cancel
	private String taskStatus;
	
	private Date taskCreateGmt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getScoresDeduction() {
		return scoresDeduction;
	}

	public void setScoresDeduction(Long scoresDeduction) {
		this.scoresDeduction = scoresDeduction;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Long getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public Long getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(Long taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getTaskTypeDesc() {
		return taskTypeDesc;
	}

	public void setTaskTypeDesc(String taskTypeDesc) {
		this.taskTypeDesc = taskTypeDesc;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskImg() {
		return taskImg;
	}

	public void setTaskImg(String taskImg) {
		this.taskImg = taskImg;
	}

	public String getAssistantType() {
		return assistantType;
	}

	public void setAssistantType(String assistantType) {
		this.assistantType = assistantType;
	}

	public Integer getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(Integer taskNumber) {
		this.taskNumber = taskNumber;
	}

	public Integer getCompletedNumber() {
		return completedNumber;
	}

	public void setCompletedNumber(Integer completedNumber) {
		this.completedNumber = completedNumber;
	}

	public String getTaskResult() {
		return taskResult;
	}

	public void setTaskResult(String taskResult) {
		this.taskResult = taskResult;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Date getTaskCreateGmt() {
		return taskCreateGmt;
	}

	public void setTaskCreateGmt(Date taskCreateGmt) {
		this.taskCreateGmt = taskCreateGmt;
	}

	public OrderDetailVO(Long id, Long orderId, Long userId, Long taskId, Long totalAmount, Long scoresDeduction,
			Integer discount, Long paymentAmount, String orderStatus, Date expireTime, Long agentId, Date gmtCreate,
			Date gmtModify, Long taskTypeId, String platform, String taskTypeDesc, String taskType, String taskImg,
			String assistantType, Integer taskNumber, Integer completedNumber, String taskResult, String taskStatus,
			Date taskCreateGmt) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.userId = userId;
		this.taskId = taskId;
		this.totalAmount = totalAmount;
		this.scoresDeduction = scoresDeduction;
		this.discount = discount;
		this.paymentAmount = paymentAmount;
		this.orderStatus = orderStatus;
		this.expireTime = expireTime;
		this.agentId = agentId;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.taskTypeId = taskTypeId;
		this.platform = platform;
		this.taskTypeDesc = taskTypeDesc;
		this.taskType = taskType;
		this.taskImg = taskImg;
		this.assistantType = assistantType;
		this.taskNumber = taskNumber;
		this.completedNumber = completedNumber;
		this.taskResult = taskResult;
		this.taskStatus = taskStatus;
		this.taskCreateGmt = taskCreateGmt;
	}

	public OrderDetailVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
