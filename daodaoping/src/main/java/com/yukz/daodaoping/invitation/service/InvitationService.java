package com.yukz.daodaoping.invitation.service;

import com.yukz.daodaoping.invitation.domain.InvitationDO;

import java.util.List;
import java.util.Map;

/**
 * 邀请表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 07:56:37
 */
public interface InvitationService {
	
	InvitationDO get(Long id);
	
	List<InvitationDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(InvitationDO invitation);
	
	int update(InvitationDO invitation);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
