package com.yukz.daodaoping.discount.dao;

import com.yukz.daodaoping.discount.domain.TaskDiscountInfoDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 任务折扣表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-10 13:20:03
 */
@Mapper
public interface TaskDiscountInfoDao {

	TaskDiscountInfoDO get(Long id);
	
	List<TaskDiscountInfoDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(TaskDiscountInfoDO taskDiscountInfo);
	
	int update(TaskDiscountInfoDO taskDiscountInfo);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
