package com.yukz.daodaoping.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yukz.daodaoping.system.domain.RoleDO;

@Service
public interface RoleService {

	RoleDO get(Long id);

	List<RoleDO> list(Map<String, Object> map);

	int save(RoleDO role);

	int update(RoleDO role);

	int remove(Long id);

	List<RoleDO> list(Long userId);

	int batchremove(Long[] ids);
}
