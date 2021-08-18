package com.yukz.daodaoping.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.yukz.daodaoping.task.dao.TaskAcceptInfoDao;
import com.yukz.daodaoping.task.domain.TaskAcceptInfoDO;
import com.yukz.daodaoping.task.service.TaskAcceptInfoService;



@Service
public class TaskAcceptInfoServiceImpl implements TaskAcceptInfoService {
	@Autowired
	private TaskAcceptInfoDao taskAcceptInfoDao;
	
	@Override
	public TaskAcceptInfoDO get(Long id){
		return taskAcceptInfoDao.get(id);
	}
	
	@Override
	public List<TaskAcceptInfoDO> list(Map<String, Object> map){
		return taskAcceptInfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return taskAcceptInfoDao.count(map);
	}
	
	@Override
	public int save(TaskAcceptInfoDO taskAcceptInfo){
		return taskAcceptInfoDao.save(taskAcceptInfo);
	}
	
	@Override
	public int update(TaskAcceptInfoDO taskAcceptInfo){
		return taskAcceptInfoDao.update(taskAcceptInfo);
	}
	
	@Override
	public int batchUpdate(List<TaskAcceptInfoDO> taskAcceptInfoList){
		return taskAcceptInfoDao.batchUpdate(taskAcceptInfoList);
	}

	@Override
	public int remove(Long id){
		return taskAcceptInfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return taskAcceptInfoDao.batchRemove(ids);
	}
	
}
