package com.yukz.daodaoping.task.dao;

import com.yukz.daodaoping.task.domain.TaskDelayOperationInfoDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 任务延迟操作信息表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-02 09:04:24
 */
@Mapper
public interface TaskDelayOperationInfoDao {

	TaskDelayOperationInfoDO get(Long id);
	
	List<TaskDelayOperationInfoDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(TaskDelayOperationInfoDO taskDelayOperationInfo);
	
	int update(TaskDelayOperationInfoDO taskDelayOperationInfo);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
