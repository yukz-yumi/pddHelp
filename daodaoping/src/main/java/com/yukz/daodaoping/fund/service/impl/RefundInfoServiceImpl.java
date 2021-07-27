package com.yukz.daodaoping.fund.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.yukz.daodaoping.fund.dao.RefundInfoDao;
import com.yukz.daodaoping.fund.domain.RefundInfoDO;
import com.yukz.daodaoping.fund.service.RefundInfoService;



@Service
public class RefundInfoServiceImpl implements RefundInfoService {
	@Autowired
	private RefundInfoDao refundInfoDao;
	
	@Override
	public RefundInfoDO get(Long id){
		return refundInfoDao.get(id);
	}
	
	@Override
	public List<RefundInfoDO> list(Map<String, Object> map){
		return refundInfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return refundInfoDao.count(map);
	}
	
	@Override
	public int save(RefundInfoDO refundInfo){
		return refundInfoDao.save(refundInfo);
	}
	
	@Override
	public int update(RefundInfoDO refundInfo){
		return refundInfoDao.update(refundInfo);
	}
	
	@Override
	public int remove(Long id){
		return refundInfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return refundInfoDao.batchRemove(ids);
	}
	
}
