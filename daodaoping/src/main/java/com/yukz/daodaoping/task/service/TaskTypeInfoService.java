package com.yukz.daodaoping.task.service;

import com.yukz.daodaoping.task.domain.TaskTypeInfoDO;

import java.util.List;
import java.util.Map;

/**
 * 任务类型表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 08:06:02
 */
public interface TaskTypeInfoService {
	
	TaskTypeInfoDO get(Long id);
	
	List<TaskTypeInfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TaskTypeInfoDO taskTypeInfo);
	
	int update(TaskTypeInfoDO taskTypeInfo);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
