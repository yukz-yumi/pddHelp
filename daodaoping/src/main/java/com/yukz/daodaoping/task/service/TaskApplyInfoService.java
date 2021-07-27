package com.yukz.daodaoping.task.service;

import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;

import java.util.List;
import java.util.Map;

/**
 * 任务申请表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 08:06:02
 */
public interface TaskApplyInfoService {
	
	TaskApplyInfoDO get(Long id);
	
	List<TaskApplyInfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TaskApplyInfoDO taskApplyInfo);
	
	int update(TaskApplyInfoDO taskApplyInfo);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
