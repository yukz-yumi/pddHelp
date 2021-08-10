package com.yukz.daodaoping.discount.service;

import com.yukz.daodaoping.discount.domain.DiscountConfigDO;

import java.util.List;
import java.util.Map;

/**
 * 折扣配置表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-10 13:20:03
 */
public interface DiscountConfigService {
	
	DiscountConfigDO get(Long id);
	
	List<DiscountConfigDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DiscountConfigDO discountConfig);
	
	int update(DiscountConfigDO discountConfig);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
