package com.yukz.daodaoping.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.yukz.daodaoping.user.dao.UserVsExAccountDao;
import com.yukz.daodaoping.user.domain.UserVsExAccountDO;
import com.yukz.daodaoping.user.service.UserVsExAccountService;



@Service
public class UserVsExAccountServiceImpl implements UserVsExAccountService {
	@Autowired
	private UserVsExAccountDao userVsExAccountDao;
	
	@Override
	public UserVsExAccountDO get(Long id){
		return userVsExAccountDao.get(id);
	}
	
	@Override
	public List<UserVsExAccountDO> list(Map<String, Object> map){
		return userVsExAccountDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return userVsExAccountDao.count(map);
	}
	
	@Override
	public int save(UserVsExAccountDO userVsExAccount){
		return userVsExAccountDao.save(userVsExAccount);
	}
	
	@Override
	public int update(UserVsExAccountDO userVsExAccount){
		return userVsExAccountDao.update(userVsExAccount);
	}
	
	@Override
	public int remove(Long id){
		return userVsExAccountDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return userVsExAccountDao.batchRemove(ids);
	}
	
}
