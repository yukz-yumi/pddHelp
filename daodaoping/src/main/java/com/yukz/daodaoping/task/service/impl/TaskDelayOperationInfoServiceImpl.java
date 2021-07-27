package com.yukz.daodaoping.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.yukz.daodaoping.task.dao.TaskDelayOperationInfoDao;
import com.yukz.daodaoping.task.domain.TaskDelayOperationInfoDO;
import com.yukz.daodaoping.task.service.TaskDelayOperationInfoService;



@Service
public class TaskDelayOperationInfoServiceImpl implements TaskDelayOperationInfoService {
	@Autowired
	private TaskDelayOperationInfoDao taskDelayOperationInfoDao;
	
	@Override
	public TaskDelayOperationInfoDO get(Long id){
		return taskDelayOperationInfoDao.get(id);
	}
	
	@Override
	public List<TaskDelayOperationInfoDO> list(Map<String, Object> map){
		return taskDelayOperationInfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return taskDelayOperationInfoDao.count(map);
	}
	
	@Override
	public int save(TaskDelayOperationInfoDO taskDelayOperationInfo){
		return taskDelayOperationInfoDao.save(taskDelayOperationInfo);
	}
	
	@Override
	public int update(TaskDelayOperationInfoDO taskDelayOperationInfo){
		return taskDelayOperationInfoDao.update(taskDelayOperationInfo);
	}
	
	@Override
	public int remove(Long id){
		return taskDelayOperationInfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return taskDelayOperationInfoDao.batchRemove(ids);
	}
	
}
