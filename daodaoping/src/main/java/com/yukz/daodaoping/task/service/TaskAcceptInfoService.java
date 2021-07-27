package com.yukz.daodaoping.task.service;

import com.yukz.daodaoping.task.domain.TaskAcceptInfoDO;

import java.util.List;
import java.util.Map;

/**
 * 任务认领表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 08:06:01
 */
public interface TaskAcceptInfoService {
	
	TaskAcceptInfoDO get(Long id);
	
	List<TaskAcceptInfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TaskAcceptInfoDO taskAcceptInfo);
	
	int update(TaskAcceptInfoDO taskAcceptInfo);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
