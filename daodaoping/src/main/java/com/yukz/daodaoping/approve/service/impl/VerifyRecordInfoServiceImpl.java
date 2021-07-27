package com.yukz.daodaoping.approve.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.yukz.daodaoping.approve.dao.VerifyRecordInfoDao;
import com.yukz.daodaoping.approve.domain.VerifyRecordInfoDO;
import com.yukz.daodaoping.approve.service.VerifyRecordInfoService;



@Service
public class VerifyRecordInfoServiceImpl implements VerifyRecordInfoService {
	@Autowired
	private VerifyRecordInfoDao verifyRecordInfoDao;
	
	@Override
	public VerifyRecordInfoDO get(Long id){
		return verifyRecordInfoDao.get(id);
	}
	
	@Override
	public List<VerifyRecordInfoDO> list(Map<String, Object> map){
		return verifyRecordInfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return verifyRecordInfoDao.count(map);
	}
	
	@Override
	public int save(VerifyRecordInfoDO verifyRecordInfo){
		return verifyRecordInfoDao.save(verifyRecordInfo);
	}
	
	@Override
	public int update(VerifyRecordInfoDO verifyRecordInfo){
		return verifyRecordInfoDao.update(verifyRecordInfo);
	}
	
	@Override
	public int remove(Long id){
		return verifyRecordInfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return verifyRecordInfoDao.batchRemove(ids);
	}
	
}
