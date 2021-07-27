package com.yukz.daodaoping.attrcnf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.yukz.daodaoping.attrcnf.dao.ExAccountTypeInfoDao;
import com.yukz.daodaoping.attrcnf.domain.ExAccountTypeInfoDO;
import com.yukz.daodaoping.attrcnf.service.ExAccountTypeInfoService;



@Service
public class ExAccountTypeInfoServiceImpl implements ExAccountTypeInfoService {
	@Autowired
	private ExAccountTypeInfoDao exAccountTypeInfoDao;
	
	@Override
	public ExAccountTypeInfoDO get(Long id){
		return exAccountTypeInfoDao.get(id);
	}
	
	@Override
	public List<ExAccountTypeInfoDO> list(Map<String, Object> map){
		return exAccountTypeInfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return exAccountTypeInfoDao.count(map);
	}
	
	@Override
	public int save(ExAccountTypeInfoDO exAccountTypeInfo){
		return exAccountTypeInfoDao.save(exAccountTypeInfo);
	}
	
	@Override
	public int update(ExAccountTypeInfoDO exAccountTypeInfo){
		return exAccountTypeInfoDao.update(exAccountTypeInfo);
	}
	
	@Override
	public int remove(Long id){
		return exAccountTypeInfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return exAccountTypeInfoDao.batchRemove(ids);
	}
	
}
