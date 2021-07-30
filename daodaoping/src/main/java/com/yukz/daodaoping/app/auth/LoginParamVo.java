package com.yukz.daodaoping.app.auth;

/**
 * 登录参数解析器
 * 
 * @author micezhao
 *
 */

public class LoginParamVo {
	
	// 用户输入的验证码
	private String validateCode;
	// 当前用户的第三方登录平台编码
	private String thirdPartUserId;
	// 用户微信昵称
	private String nickName;
	// 用户头像
	private String headImgUrl;
	// 用户联系方式
	private String mobile;
	// 用户状态:unverified 未认证/ verified 已认证/ forbidden 已停用
	private String userStatus;
	// 机构编号
	private Long agentId;
	
	public String getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	public String getThirdPartUserId() {
		return thirdPartUserId;
	}
	public void setThirdPartUserId(String thirdPartUserId) {
		this.thirdPartUserId = thirdPartUserId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public Long getAgentId() {
		return agentId;
	}
	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
	public LoginParamVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
