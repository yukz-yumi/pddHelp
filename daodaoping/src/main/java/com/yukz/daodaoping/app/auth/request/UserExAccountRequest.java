package com.yukz.daodaoping.app.auth.request;

/**
 * 用户账号绑定请求对象
 * 
 * @author micezhao
 *
 */
public class UserExAccountRequest {

	private Long userId;

	private Long agentId;
	// 账号类型
	private String accountType;
	// 账号
	private String account;
	// 用户是否启用: yes/no
	private String allowed;
	// 系统为当前账号设定的状态 available/forbidden
	private String accountStatus;
	// 账号截图图片地址
	private String accountImg;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getAgentId() {
		return agentId;
	}
	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAllowed() {
		return allowed;
	}
	public void setAllowed(String allowed) {
		this.allowed = allowed;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getAccountImg() {
		return accountImg;
	}
	public void setAccountImg(String accountImg) {
		this.accountImg = accountImg;
	}
	
	

}
