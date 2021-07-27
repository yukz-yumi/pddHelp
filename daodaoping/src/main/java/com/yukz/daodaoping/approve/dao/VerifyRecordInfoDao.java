package com.yukz.daodaoping.approve.dao;

import com.yukz.daodaoping.approve.domain.VerifyRecordInfoDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 任务审核记录表
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 08:09:51
 */
@Mapper
public interface VerifyRecordInfoDao {

	VerifyRecordInfoDO get(Long id);
	
	List<VerifyRecordInfoDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(VerifyRecordInfoDO verifyRecordInfo);
	
	int update(VerifyRecordInfoDO verifyRecordInfo);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
