package com.yukz.daodaoping.feedback.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.yukz.daodaoping.feedback.dao.FeedbackInfoDao;
import com.yukz.daodaoping.feedback.domain.FeedbackInfoDO;
import com.yukz.daodaoping.feedback.service.FeedbackInfoService;



@Service
public class FeedbackInfoServiceImpl implements FeedbackInfoService {
	@Autowired
	private FeedbackInfoDao feedbackInfoDao;
	
	@Override
	public FeedbackInfoDO get(Long id){
		return feedbackInfoDao.get(id);
	}
	
	@Override
	public List<FeedbackInfoDO> list(Map<String, Object> map){
		return feedbackInfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return feedbackInfoDao.count(map);
	}
	
	@Override
	public int save(FeedbackInfoDO feedbackInfo){
		return feedbackInfoDao.save(feedbackInfo);
	}
	
	@Override
	public int update(FeedbackInfoDO feedbackInfo){
		return feedbackInfoDao.update(feedbackInfo);
	}
	
	@Override
	public int remove(Long id){
		return feedbackInfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return feedbackInfoDao.batchRemove(ids);
	}
	
}
