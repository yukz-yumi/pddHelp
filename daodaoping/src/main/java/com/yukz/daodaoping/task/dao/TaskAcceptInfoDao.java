package com.yukz.daodaoping.task.dao;

import com.yukz.daodaoping.task.domain.TaskAcceptInfoDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 任务认领表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-14 10:18:25
 */
@Mapper
public interface TaskAcceptInfoDao {

	TaskAcceptInfoDO get(Long id);
	
	List<TaskAcceptInfoDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(TaskAcceptInfoDO taskAcceptInfo);
	
	int update(TaskAcceptInfoDO taskAcceptInfo);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
