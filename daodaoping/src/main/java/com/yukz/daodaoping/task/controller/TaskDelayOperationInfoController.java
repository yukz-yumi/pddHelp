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
import com.yukz.daodaoping.task.domain.TaskDelayOperationInfoDO;
import com.yukz.daodaoping.task.service.TaskDelayOperationInfoService;

/**
 * 任务延迟操作信息表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-02 08:33:34
 */
 
@Controller
@RequestMapping("/task/taskDelayOperationInfo")
public class TaskDelayOperationInfoController {
	@Autowired
	private TaskDelayOperationInfoService taskDelayOperationInfoService;
	
	@GetMapping()
	@RequiresPermissions("task:taskDelayOperationInfo:taskDelayOperationInfo")
	String TaskDelayOperationInfo(){
	    return "task/taskDelayOperationInfo/taskDelayOperationInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("task:taskDelayOperationInfo:taskDelayOperationInfo")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<TaskDelayOperationInfoDO> taskDelayOperationInfoList = taskDelayOperationInfoService.list(query);
		int total = taskDelayOperationInfoService.count(query);
		PageUtils pageUtils = new PageUtils(taskDelayOperationInfoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("task:taskDelayOperationInfo:add")
	String add(){
	    return "task/taskDelayOperationInfo/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("task:taskDelayOperationInfo:edit")
	String edit(@PathVariable("id") Long id,Model model){
		TaskDelayOperationInfoDO taskDelayOperationInfo = taskDelayOperationInfoService.get(id);
		model.addAttribute("taskDelayOperationInfo", taskDelayOperationInfo);
	    return "task/taskDelayOperationInfo/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("task:taskDelayOperationInfo:add")
	public R save( TaskDelayOperationInfoDO taskDelayOperationInfo){
		if(taskDelayOperationInfoService.save(taskDelayOperationInfo)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("task:taskDelayOperationInfo:edit")
	public R update( TaskDelayOperationInfoDO taskDelayOperationInfo){
		taskDelayOperationInfoService.update(taskDelayOperationInfo);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("task:taskDelayOperationInfo:remove")
	public R remove( Long id){
		if(taskDelayOperationInfoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("task:taskDelayOperationInfo:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		taskDelayOperationInfoService.batchRemove(ids);
		return R.ok();
	}
	
}
