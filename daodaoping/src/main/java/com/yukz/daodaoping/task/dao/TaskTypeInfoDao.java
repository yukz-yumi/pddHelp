package com.yukz.daodaoping.task.dao;

import com.yukz.daodaoping.task.domain.TaskTypeInfoDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 任务类型表
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 08:06:02
 */
@Mapper
public interface TaskTypeInfoDao {

	TaskTypeInfoDO get(Long id);
	
	List<TaskTypeInfoDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(TaskTypeInfoDO taskTypeInfo);
	
	int update(TaskTypeInfoDO taskTypeInfo);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
