package com.yukz.daodaoping.invitation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.yukz.daodaoping.invitation.dao.InvitationDao;
import com.yukz.daodaoping.invitation.domain.InvitationDO;
import com.yukz.daodaoping.invitation.service.InvitationService;



@Service
public class InvitationServiceImpl implements InvitationService {
	@Autowired
	private InvitationDao invitationDao;
	
	@Override
	public InvitationDO get(Long id){
		return invitationDao.get(id);
	}
	
	@Override
	public List<InvitationDO> list(Map<String, Object> map){
		return invitationDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return invitationDao.count(map);
	}
	
	@Override
	public int save(InvitationDO invitation){
		return invitationDao.save(invitation);
	}
	
	@Override
	public int update(InvitationDO invitation){
		return invitationDao.update(invitation);
	}
	
	@Override
	public int remove(Long id){
		return invitationDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return invitationDao.batchRemove(ids);
	}
	
}
