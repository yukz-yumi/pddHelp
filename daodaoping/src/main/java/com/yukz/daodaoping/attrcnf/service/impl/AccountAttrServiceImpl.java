package com.yukz.daodaoping.attrcnf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.yukz.daodaoping.attrcnf.dao.AccountAttrDao;
import com.yukz.daodaoping.attrcnf.domain.AccountAttrDO;
import com.yukz.daodaoping.attrcnf.service.AccountAttrService;



@Service
public class AccountAttrServiceImpl implements AccountAttrService {
	@Autowired
	private AccountAttrDao accountAttrDao;
	
	@Override
	public AccountAttrDO get(Long id){
		return accountAttrDao.get(id);
	}
	
	@Override
	public List<AccountAttrDO> list(Map<String, Object> map){
		return accountAttrDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return accountAttrDao.count(map);
	}
	
	@Override
	public int save(AccountAttrDO accountAttr){
		return accountAttrDao.save(accountAttr);
	}
	
	@Override
	public int update(AccountAttrDO accountAttr){
		return accountAttrDao.update(accountAttr);
	}
	
	@Override
	public int remove(Long id){
		return accountAttrDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return accountAttrDao.batchRemove(ids);
	}
	
}
