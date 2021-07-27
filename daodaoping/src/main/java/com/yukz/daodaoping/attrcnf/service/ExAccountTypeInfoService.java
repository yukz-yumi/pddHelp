package com.yukz.daodaoping.attrcnf.service;

import com.yukz.daodaoping.attrcnf.domain.ExAccountTypeInfoDO;

import java.util.List;
import java.util.Map;

/**
 * 外部账号类型表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 07:52:47
 */
public interface ExAccountTypeInfoService {
	
	ExAccountTypeInfoDO get(Long id);
	
	List<ExAccountTypeInfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ExAccountTypeInfoDO exAccountTypeInfo);
	
	int update(ExAccountTypeInfoDO exAccountTypeInfo);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
