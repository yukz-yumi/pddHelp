package com.yukz.daodaoping.app.invitation;


public class InvitationBO {
	
	private Long userId;
	
	private int invitorNum = 0;
	
	private Long AgentId;
	//间接推荐人id
	private Long indirectUserId;
	//直接推荐人id
	private Long directUserId;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public int getInvitorNum() {
		return invitorNum;
	}
	public void setInvitorNum(int invitorNum) {
		this.invitorNum = invitorNum;
	}
	public Long getAgentId() {
		return AgentId;
	}
	public void setAgentId(Long agentId) {
		AgentId = agentId;
	}
	public Long getIndirectUserId() {
		return indirectUserId;
	}
	public void setIndirectUserId(Long indirectUserId) {
		this.indirectUserId = indirectUserId;
	}
	public Long getDirectUserId() {
		return directUserId;
	}
	public void setDirectUserId(Long directUserId) {
		this.directUserId = directUserId;
	}
	public InvitationBO(Long userId, int invitorNum, Long agentId, Long indirectUserId, Long directUserId) {
		super();
		this.userId = userId;
		this.invitorNum = invitorNum;
		AgentId = agentId;
		this.indirectUserId = indirectUserId;
		this.directUserId = directUserId;
	}
	public InvitationBO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
