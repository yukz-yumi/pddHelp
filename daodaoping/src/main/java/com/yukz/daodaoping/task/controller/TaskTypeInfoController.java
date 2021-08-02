package com.yukz.daodaoping.task.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yukz.daodaoping.common.utils.PageUtils;
import com.yukz.daodaoping.common.utils.Query;
import com.yukz.daodaoping.common.utils.R;
import com.yukz.daodaoping.task.domain.TaskTypeInfoDO;
import com.yukz.daodaoping.task.service.TaskTypeInfoService;

/**
 * 任务类型表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-02 09:04:24
 */
 
@Controller
@RequestMapping("/task/taskTypeInfo")
public class TaskTypeInfoController {
	@Autowired
	private TaskTypeInfoService taskTypeInfoService;
	
	@GetMapping()
	@RequiresPermissions("task:taskTypeInfo:taskTypeInfo")
	String TaskTypeInfo(){
	    return "task/taskTypeInfo/taskTypeInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("task:taskTypeInfo:taskTypeInfo")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<TaskTypeInfoDO> taskTypeInfoList = taskTypeInfoService.list(query);
		int total = taskTypeInfoService.count(query);
		PageUtils pageUtils = new PageUtils(taskTypeInfoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("task:taskTypeInfo:add")
	String add(){
	    return "task/taskTypeInfo/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("task:taskTypeInfo:edit")
	String edit(@PathVariable("id") Long id,Model model){
		TaskTypeInfoDO taskTypeInfo = taskTypeInfoService.get(id);
		model.addAttribute("taskTypeInfo", taskTypeInfo);
	    return "task/taskTypeInfo/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("task:taskTypeInfo:add")
	public R save( TaskTypeInfoDO taskTypeInfo){
		if(taskTypeInfoService.save(taskTypeInfo)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("task:taskTypeInfo:edit")
	public R update( TaskTypeInfoDO taskTypeInfo){
		taskTypeInfoService.update(taskTypeInfo);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("task:taskTypeInfo:remove")
	public R remove( Long id){
		if(taskTypeInfoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("task:taskTypeInfo:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		taskTypeInfoService.batchRemove(ids);
		return R.ok();
	}
	
}
