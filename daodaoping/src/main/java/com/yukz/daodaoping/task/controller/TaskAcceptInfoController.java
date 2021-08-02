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
import com.yukz.daodaoping.task.domain.TaskAcceptInfoDO;
import com.yukz.daodaoping.task.service.TaskAcceptInfoService;

/**
 * 任务认领表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-02 08:33:33
 */
 
@Controller
@RequestMapping("/task/taskAcceptInfo")
public class TaskAcceptInfoController {
	@Autowired
	private TaskAcceptInfoService taskAcceptInfoService;
	
	@GetMapping()
	@RequiresPermissions("task:taskAcceptInfo:taskAcceptInfo")
	String TaskAcceptInfo(){
	    return "task/taskAcceptInfo/taskAcceptInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("task:taskAcceptInfo:taskAcceptInfo")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<TaskAcceptInfoDO> taskAcceptInfoList = taskAcceptInfoService.list(query);
		int total = taskAcceptInfoService.count(query);
		PageUtils pageUtils = new PageUtils(taskAcceptInfoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("task:taskAcceptInfo:add")
	String add(){
	    return "task/taskAcceptInfo/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("task:taskAcceptInfo:edit")
	String edit(@PathVariable("id") Long id,Model model){
		TaskAcceptInfoDO taskAcceptInfo = taskAcceptInfoService.get(id);
		model.addAttribute("taskAcceptInfo", taskAcceptInfo);
	    return "task/taskAcceptInfo/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("task:taskAcceptInfo:add")
	public R save( TaskAcceptInfoDO taskAcceptInfo){
		if(taskAcceptInfoService.save(taskAcceptInfo)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("task:taskAcceptInfo:edit")
	public R update( TaskAcceptInfoDO taskAcceptInfo){
		taskAcceptInfoService.update(taskAcceptInfo);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("task:taskAcceptInfo:remove")
	public R remove( Long id){
		if(taskAcceptInfoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("task:taskAcceptInfo:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		taskAcceptInfoService.batchRemove(ids);
		return R.ok();
	}
	
}
