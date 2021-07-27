package com.yukz.daodaoping.fund.service;

import com.yukz.daodaoping.fund.domain.RefundInfoDO;

import java.util.List;
import java.util.Map;

/**
 * 退款记录表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 07:51:04
 */
public interface RefundInfoService {
	
	RefundInfoDO get(Long id);
	
	List<RefundInfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(RefundInfoDO refundInfo);
	
	int update(RefundInfoDO refundInfo);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
