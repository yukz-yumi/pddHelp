package com.yukz.daodaoping.feedback.service;

import com.yukz.daodaoping.feedback.domain.FeedbackInfoDO;

import java.util.List;
import java.util.Map;

/**
 * 用户反馈记录
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 08:44:17
 */
public interface FeedbackInfoService {
	
	FeedbackInfoDO get(Long id);
	
	List<FeedbackInfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(FeedbackInfoDO feedbackInfo);
	
	int update(FeedbackInfoDO feedbackInfo);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
