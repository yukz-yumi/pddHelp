package com.yukz.daodaoping.fund.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.yukz.daodaoping.fund.dao.FundTransferInfoDao;
import com.yukz.daodaoping.fund.domain.FundTransferInfoDO;
import com.yukz.daodaoping.fund.service.FundTransferInfoService;



@Service
public class FundTransferInfoServiceImpl implements FundTransferInfoService {
	@Autowired
	private FundTransferInfoDao fundTransferInfoDao;
	
	@Override
	public FundTransferInfoDO get(Long id){
		return fundTransferInfoDao.get(id);
	}
	
	@Override
	public List<FundTransferInfoDO> list(Map<String, Object> map){
		return fundTransferInfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return fundTransferInfoDao.count(map);
	}
	
	@Override
	public int save(FundTransferInfoDO fundTransferInfo){
		return fundTransferInfoDao.save(fundTransferInfo);
	}
	
	@Override
	public int update(FundTransferInfoDO fundTransferInfo){
		return fundTransferInfoDao.update(fundTransferInfo);
	}
	
	@Override
	public int remove(Long id){
		return fundTransferInfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return fundTransferInfoDao.batchRemove(ids);
	}
	
}
