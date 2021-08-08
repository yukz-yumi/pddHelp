package com.yukz.daodaoping.app.auth.vo;

import java.util.ArrayList;
import java.util.List;

import com.yukz.daodaoping.user.domain.UserVsExAccountDO;

/**
 * 用户与外部账号绑定视图对象
 * @author micezhao
 *
 */
public class UserExAccountVo {
	
	private Long userId;
	
	private String openId;
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
	
	private List<UserVsExAccountDO> exAccountList = new ArrayList<UserVsExAccountDO>();

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public List<UserVsExAccountDO> getExAccountList() {
		return exAccountList;
	}

	public void setExAccountList(List<UserVsExAccountDO> exAccountList) {
		this.exAccountList = exAccountList;
	}
	
	
}
