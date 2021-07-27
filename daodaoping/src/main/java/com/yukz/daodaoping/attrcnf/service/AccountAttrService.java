package com.yukz.daodaoping.attrcnf.service;

import com.yukz.daodaoping.attrcnf.domain.AccountAttrDO;

import java.util.List;
import java.util.Map;

/**
 * 账号属性配置表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 07:52:47
 */
public interface AccountAttrService {
	
	AccountAttrDO get(Long id);
	
	List<AccountAttrDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(AccountAttrDO accountAttr);
	
	int update(AccountAttrDO accountAttr);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
