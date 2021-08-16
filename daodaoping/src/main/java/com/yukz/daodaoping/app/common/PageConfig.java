package com.yukz.daodaoping.app.common;

import java.util.ArrayList;
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
import com.yukz.daodaoping.app.task.enums.TaskAcceptionStatusEunm;
import com.yukz.daodaoping.app.task.enums.TaskIsPaidEnum;
import com.yukz.daodaoping.app.task.enums.TaskStatusEnum;
import com.yukz.daodaoping.app.task.enums.TaskVerifyStatusEnum;
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
	public R getTaskTypeInfo() {
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
	@GetMapping("platform/{platform}/list/{agentId}")
	public R getAllTaskType(@PathVariable("platform") String platform,@PathVariable("agentId") Long agentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("agentId", agentId);
		map.put("platform", platform);
		map.put("allowed", IsAllowEnum.YES.getStatus());
		map.put("sort", "id");
		map.put("order", "asc");
		List<TaskTypeInfoDO> taskTypeInfoList = taskTypeInfoService.list(map);
		return R.ok().put("data", taskTypeInfoList);
	}
	
	@GetMapping("assisantType")
	public R getAssistantTypeList() {
		return R.ok().put("data", AssisantTypeEnum.toList());
	}
	
	@GetMapping("taskStatus")
	public R getTaskStatusList() {
		return R.ok().put("data", TaskStatusEnum.toList());
	}
	
	@GetMapping("timeScope")
	public R getTimeScopeList() {
		return R.ok().put("data", TimeScopeEnum.toList());
	}
	
	@GetMapping("taskType/{agentId}")
	public R getTaskTypeList(@PathVariable("agentId") Long agentId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("agentId", agentId);
		map.put("allowed", "yes");
		List<Map<String,String>> resultMapList = new ArrayList<Map<String,String>>();
		List<TaskTypeInfoDO> list  = taskTypeInfoService.list(map);
		if(list.isEmpty()) {
			return  R.ok().put("data", list);
		}
		for (TaskTypeInfoDO item : list) {
			Map<String,String> typeMap = new HashMap<String, String>();
			typeMap.put("code", item.getTaskType());
			typeMap.put("desc", item.getTaskTypeDesc());
			resultMapList.add(typeMap);
		}
		return R.ok().put("data", resultMapList);
	}
	
	
	@GetMapping("/taskAcceptionStatus")
	public R getTaskApplyList() {
		return R.ok().put("data",  TaskAcceptionStatusEunm.toList());
	}
	
	@GetMapping("/taskVerifyStatus")
	public R getTaskVerifyStatus() {
		return R.ok().put("data",  TaskVerifyStatusEnum.toList());
	}
	
	@GetMapping("/taskIsPaid")
	public R getTaskPaidStatus() {
		return R.ok().put("data",  TaskIsPaidEnum.toList());
	}
}
