package com.yukz.daodaoping.system.service.impl;

import com.yukz.daodaoping.system.config.RedisHandler;
import com.yukz.daodaoping.system.dao.SysSetDao;
import com.yukz.daodaoping.system.domain.SysSetDO;
import com.yukz.daodaoping.system.service.SysSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service
public class SysSetServiceImpl implements SysSetService {
	@Autowired
	private SysSetDao sysSetDao;
	@Autowired
	private RedisHandler redisHandler;
	
	@Override
//	@Cacheable(value="sysSet", key = "'sysSet-'+#p0")
	public SysSetDO get(Long id){
		return sysSetDao.get(id);
	}
	
	@Override
//	@Cacheable(value="sysSet", key = "'sysSet-'+#p0+'-'+#p1+'-'+#p2+'-'+#p3")
	public SysSetDO getByKey(String setKey, String platform, String setType, Long agentId){
		return sysSetDao.getByKey(setKey, platform, setType, agentId);
	}

	@Override
	public List<SysSetDO> listFromRedisHandle(List<Object> setKeyList, String platform, String setType, Long agentId){
		List<Object> cacheList = redisHandler.getListByKeys(setKeyList);
		return null;
	}

	@Override
	public List<SysSetDO> list(Map<String, Object> map){
		return sysSetDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return sysSetDao.count(map);
	}
	
	@Override
//	@CachePut(value="sysSet", key = "'sysSet-'+ #sysSet.id")
	public int save(SysSetDO sysSet){
		return sysSetDao.save(sysSet);
	}
	
	@Override
//	@CachePut(value="sysSet", key = "'sysSet-'+ #sysSet.id")
	public int update(SysSetDO sysSet){
		return sysSetDao.update(sysSet);
	}
	
	@Override
//	@CacheEvict(value="sysSet", key = "'sysSet-'+#p0")
	public int remove(Long id){
		return sysSetDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return sysSetDao.batchRemove(ids);
	}
	
}
