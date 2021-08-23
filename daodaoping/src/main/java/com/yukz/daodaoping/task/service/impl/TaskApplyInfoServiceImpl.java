package com.yukz.daodaoping.task.service.impl;

import com.yukz.daodaoping.app.task.vo.TaskDetailVO;
import com.yukz.daodaoping.task.dao.TaskApplyInfoDao;
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;
import com.yukz.daodaoping.task.service.TaskApplyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service
public class TaskApplyInfoServiceImpl implements TaskApplyInfoService {
	@Autowired
	private TaskApplyInfoDao taskApplyInfoDao;
	
	@Override
	public TaskApplyInfoDO get(Long id){
		return taskApplyInfoDao.get(id);
	}
	
	@Override
	public TaskApplyInfoDO getByIdForupdate(Long id){
		return taskApplyInfoDao.getByIdForupdate(id);
	}

	@Override
	public List<TaskApplyInfoDO> list(Map<String, Object> map){
		return taskApplyInfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return taskApplyInfoDao.count(map);
	}
	
	@Override
	public int save(TaskApplyInfoDO taskApplyInfo){
		return taskApplyInfoDao.save(taskApplyInfo);
	}
	
	@Override
	public int update(TaskApplyInfoDO taskApplyInfo){
		return taskApplyInfoDao.update(taskApplyInfo);
	}
	
	@Override
	public int remove(Long id){
		return taskApplyInfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return taskApplyInfoDao.batchRemove(ids);
	}

	@Override
	public List<TaskDetailVO> getTaskDetailList(Map<String, Object> map) {
		
		return taskApplyInfoDao.getTaskDetailList(map);
	}
	
	
	
}
