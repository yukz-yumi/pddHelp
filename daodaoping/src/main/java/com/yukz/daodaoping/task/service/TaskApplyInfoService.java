package com.yukz.daodaoping.task.service;

import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;

import java.util.List;
import java.util.Map;

/**
 * 任务申请表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-09 10:36:56
 */
public interface TaskApplyInfoService {
	
	TaskApplyInfoDO get(Long id);
	
	TaskApplyInfoDO getByIdForupdate(Long id);

	List<TaskApplyInfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TaskApplyInfoDO taskApplyInfo);
	
	int update(TaskApplyInfoDO taskApplyInfo);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
