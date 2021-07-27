package com.yukz.daodaoping.feedback.dao;

import com.yukz.daodaoping.feedback.domain.FeedbackInfoDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用户反馈记录
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 08:44:17
 */
@Mapper
public interface FeedbackInfoDao {

	FeedbackInfoDO get(Long id);
	
	List<FeedbackInfoDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(FeedbackInfoDO feedbackInfo);
	
	int update(FeedbackInfoDO feedbackInfo);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
