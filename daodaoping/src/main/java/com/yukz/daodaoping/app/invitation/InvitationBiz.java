package com.yukz.daodaoping.app.invitation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yukz.daodaoping.invitation.domain.InvitationDO;
import com.yukz.daodaoping.invitation.service.InvitationService;

@Service
public class InvitationBiz {
	
	@Autowired
	private InvitationService invitationService;
	
	public InvitationBO getInvitationShip(Long UserId,Long AgentId) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<InvitationDO> invitationList= invitationService.list(map);
		InvitationBO bo = new InvitationBO();
		bo.setAgentId(AgentId);
		bo.setUserId(UserId);
		if(invitationList.isEmpty()) {
			bo.setInvitorNum(0);
		}else {
			InvitationDO invitationItem = invitationList.get(0);
			if(invitationItem.getDirectUserId() != null) {
				bo.setInvitorNum(bo.getInvitorNum()+1);
				bo.setDirectUserId(invitationItem.getDirectUserId());
			}
			if(invitationItem.getIndirectUserId()!= null) {
				bo.setInvitorNum(bo.getInvitorNum()+1);
				bo.setIndirectUserId(invitationItem.getIndirectUserId());
			}
		}
		return bo;
	}
	
}
