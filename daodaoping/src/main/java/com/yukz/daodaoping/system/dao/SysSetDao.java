package com.yukz.daodaoping.system.dao;

import com.yukz.daodaoping.system.domain.SysSetDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 系统设置表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-18 15:55:04
 */
@Mapper
public interface SysSetDao {

	SysSetDO get(Long id);
	
	SysSetDO getByKey(String setKey, String platform, String setType, Long agentId);

	List<SysSetDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(SysSetDO sysSet);
	
	int update(SysSetDO sysSet);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
