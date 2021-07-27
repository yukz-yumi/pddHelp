package com.yukz.daodaoping.fund.dao;

import com.yukz.daodaoping.fund.domain.FundTransferInfoDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 资金流水表
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 07:51:04
 */
@Mapper
public interface FundTransferInfoDao {

	FundTransferInfoDO get(Long id);
	
	List<FundTransferInfoDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(FundTransferInfoDO fundTransferInfo);
	
	int update(FundTransferInfoDO fundTransferInfo);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
