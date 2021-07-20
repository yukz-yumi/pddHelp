package com.yukz.daodaoping.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yukz.daodaoping.common.domain.LogDO;
import com.yukz.daodaoping.common.domain.PageDO;
import com.yukz.daodaoping.common.utils.Query;
@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
