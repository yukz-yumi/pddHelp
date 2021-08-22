package com.yukz.daodaoping.app.auth.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.yukz.daodaoping.user.domain.UserVsExAccountDO;

public class UserAgent implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5710919441152048062L;
	
	private String sessionId;
	
	private Long userId;

	private Long agentId;

	private String openId;
	// 用户微信昵称
	private String nickName;
	// 用户头像
	private String headImgUrl;
	// 用户当前积分
	private Long scores;
	// 用户等级
	private String userGrade;
	// 用户联系方式
	private String mobile;
	// 用户状态:unverified 未认证/ verified 已认证/ forbidden 已停用
	private String userStatus;

	private List<UserVsExAccountDO> exAccountList = new ArrayList<UserVsExAccountDO>();
	
	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

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

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
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

	public Long getScores() {
		return scores;
	}

	public void setScores(Long scores) {
		this.scores = scores;
	}

	public String getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
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

	public List<UserVsExAccountDO> getExAccountList() {
		return exAccountList;
	}

	public void setExAccountList(List<UserVsExAccountDO> exAccountList) {
		this.exAccountList = exAccountList;
	}
	
	public UserAgent(String sessionId, Long userId, Long agentId, String openId, String nickName, String headImgUrl,
			Long scores, String userGrade, String mobile, String userStatus, List<UserVsExAccountDO> exAccountList) {
		super();
		this.sessionId = sessionId;
		this.userId = userId;
		this.agentId = agentId;
		this.openId = openId;
		this.nickName = nickName;
		this.headImgUrl = headImgUrl;
		this.scores = scores;
		this.userGrade = userGrade;
		this.mobile = mobile;
		this.userStatus = userStatus;
		this.exAccountList = exAccountList;
	}

	public UserAgent() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
