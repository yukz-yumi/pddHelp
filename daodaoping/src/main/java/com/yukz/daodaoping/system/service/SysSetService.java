package com.yukz.daodaoping.system.service;

import com.yukz.daodaoping.system.domain.SysSetDO;

import java.util.List;
import java.util.Map;

/**
 * 系统设置表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-18 15:55:04
 */
public interface SysSetService {
	
	SysSetDO get(Long id);

	/**
	 * 根据key获取系统配置
	 * @param setKey 系统设置key
	 * @param platform 平台编码 没有则传递null
	 * @param setType 任务类型 没有则传递null
	 * @return
	 */
	SysSetDO getByKey(String setKey, String platform, String setType, Long agentId);

	List<SysSetDO> listFromRedisHandle(List<Object> setKeyList, String platform, String setType, Long agentId);

	List<SysSetDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SysSetDO sysSet);
	
	int update(SysSetDO sysSet);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
