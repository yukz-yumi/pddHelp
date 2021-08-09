package com.yukz.daodaoping.app.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yukz.daodaoping.app.auth.vo.UserAgent;
import com.yukz.daodaoping.app.enums.IsAllowEnum;
import com.yukz.daodaoping.common.utils.R;
import com.yukz.daodaoping.task.domain.TaskTypeInfoDO;
import com.yukz.daodaoping.task.enums.PlatformEnum;
import com.yukz.daodaoping.task.service.TaskTypeInfoService;

@RestController
@RequestMapping("appInt/pageConfig/")
public class PageConfig {
	
	@Autowired
	private TaskTypeInfoService taskTypeInfoService;
	
	@GetMapping("supplyPlatform")
	public R getTaskTypeInfo(UserAgent userAgent) {
		return R.ok().put("data", PlatformEnum.toMap());
	}
	
	/**
	 * 查询不同平台所支持的业务
	 * @param agentId
	 * @param platform
	 * @param pageNum
	 * @param pageSize
	 * @param userAgent
	 * @return
	 */
	@GetMapping("platform/{platform}/list")
	public R getAllTaskType(@PathVariable("platform") String platform,UserAgent userAgent) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("agentId", userAgent.getAgentId());
		map.put("platform", platform);
		map.put("allowed", IsAllowEnum.YES.getStatus());
		map.put("sort", "id");
		map.put("order", "asc");
		List<TaskTypeInfoDO> taskTypeInfoList = taskTypeInfoService.list(map);
		return R.ok().put("data", taskTypeInfoList);
	}
	
	@GetMapping("assisantType")
	public R getAssistantTypeList(UserAgent userAgent) {
		return R.ok().put("data", AssisantTypeEnum.toList());
	}
	
}
