package com.yukz.daodaoping.discount.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.yukz.daodaoping.discount.dao.TaskDiscountInfoDao;
import com.yukz.daodaoping.discount.domain.TaskDiscountInfoDO;
import com.yukz.daodaoping.discount.service.TaskDiscountInfoService;



@Service
public class TaskDiscountInfoServiceImpl implements TaskDiscountInfoService {
	@Autowired
	private TaskDiscountInfoDao taskDiscountInfoDao;
	
	@Override
	public TaskDiscountInfoDO get(Long id){
		return taskDiscountInfoDao.get(id);
	}
	
	@Override
	public List<TaskDiscountInfoDO> list(Map<String, Object> map){
		return taskDiscountInfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return taskDiscountInfoDao.count(map);
	}
	
	@Override
	public int save(TaskDiscountInfoDO taskDiscountInfo){
		return taskDiscountInfoDao.save(taskDiscountInfo);
	}
	
	@Override
	public int update(TaskDiscountInfoDO taskDiscountInfo){
		return taskDiscountInfoDao.update(taskDiscountInfo);
	}
	
	@Override
	public int remove(Long id){
		return taskDiscountInfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return taskDiscountInfoDao.batchRemove(ids);
	}
	
}
