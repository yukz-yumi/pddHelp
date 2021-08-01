package com.yukz.daodaoping.app.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yukz.daodaoping.app.enums.IsAllowEnum;
import com.yukz.daodaoping.task.domain.TaskTypeInfoDO;
import com.yukz.daodaoping.task.service.TaskTypeInfoService;

@RequestMapping("appInt/task/")
@RestController
public class TaskCtrl {
	
	@Autowired
	private TaskTypeInfoService taskTypeInfoService;
	
	@GetMapping("list/{agentId}/{pageNum}/{pageSize}")
	public PageInfo<TaskTypeInfoDO> getAllTaskType(@PathVariable("agentId") Long agentId,
			@PathVariable("pageNum") int pageNum , @PathVariable("pageSize") int pageSize){
		PageHelper.startPage(pageNum, pageSize);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("agent_id", agentId);
		map.put("allowed",IsAllowEnum.YES.getStatus());
		List<TaskTypeInfoDO> taskTypeInfoList = taskTypeInfoService.list(map);
		PageInfo<TaskTypeInfoDO> pageResult = new PageInfo<TaskTypeInfoDO>(taskTypeInfoList);
		return pageResult;
	}
	
	
	
	
	
	
	
	
}
