package com.yukz.daodaoping.user.service;

import com.yukz.daodaoping.user.domain.UserVsExAccountDO;

import java.util.List;
import java.util.Map;

/**
 * 用户与外部账号映射表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 08:07:22
 */
public interface UserVsExAccountService {
	
	UserVsExAccountDO get(Long id);
	
	List<UserVsExAccountDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(UserVsExAccountDO userVsExAccount);
	
	int update(UserVsExAccountDO userVsExAccount);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
