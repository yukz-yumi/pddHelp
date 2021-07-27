package com.yukz.daodaoping.user.dao;

import com.yukz.daodaoping.user.domain.UserInfoDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息表
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 08:07:22
 */
@Mapper
public interface UserInfoDao {

	UserInfoDO get(Long id);
	
	List<UserInfoDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(UserInfoDO userInfo);
	
	int update(UserInfoDO userInfo);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
