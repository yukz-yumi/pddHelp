package com.yukz.daodaoping.discount.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.yukz.daodaoping.discount.dao.DiscountConfigDao;
import com.yukz.daodaoping.discount.domain.DiscountConfigDO;
import com.yukz.daodaoping.discount.service.DiscountConfigService;



@Service
public class DiscountConfigServiceImpl implements DiscountConfigService {
	@Autowired
	private DiscountConfigDao discountConfigDao;
	
	@Override
	public DiscountConfigDO get(Long id){
		return discountConfigDao.get(id);
	}
	
	@Override
	public List<DiscountConfigDO> list(Map<String, Object> map){
		return discountConfigDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return discountConfigDao.count(map);
	}
	
	@Override
	public int save(DiscountConfigDO discountConfig){
		return discountConfigDao.save(discountConfig);
	}
	
	@Override
	public int update(DiscountConfigDO discountConfig){
		return discountConfigDao.update(discountConfig);
	}
	
	@Override
	public int remove(Long id){
		return discountConfigDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return discountConfigDao.batchRemove(ids);
	}
	
}
