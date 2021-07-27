package com.yukz.daodaoping.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.yukz.daodaoping.task.dao.TaskTypeInfoDao;
import com.yukz.daodaoping.task.domain.TaskTypeInfoDO;
import com.yukz.daodaoping.task.service.TaskTypeInfoService;



@Service
public class TaskTypeInfoServiceImpl implements TaskTypeInfoService {
	@Autowired
	private TaskTypeInfoDao taskTypeInfoDao;
	
	@Override
	public TaskTypeInfoDO get(Long id){
		return taskTypeInfoDao.get(id);
	}
	
	@Override
	public List<TaskTypeInfoDO> list(Map<String, Object> map){
		return taskTypeInfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return taskTypeInfoDao.count(map);
	}
	
	@Override
	public int save(TaskTypeInfoDO taskTypeInfo){
		return taskTypeInfoDao.save(taskTypeInfo);
	}
	
	@Override
	public int update(TaskTypeInfoDO taskTypeInfo){
		return taskTypeInfoDao.update(taskTypeInfo);
	}
	
	@Override
	public int remove(Long id){
		return taskTypeInfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return taskTypeInfoDao.batchRemove(ids);
	}
	
}
