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
import com.yukz.daodaoping.task.domain.TaskApplyInfoDO;
import com.yukz.daodaoping.task.service.TaskApplyInfoService;

/**
 * 任务申请表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-02 09:04:23
 */
 
@Controller
@RequestMapping("/task/taskApplyInfo")
public class TaskApplyInfoController {
	@Autowired
	private TaskApplyInfoService taskApplyInfoService;
	
	@GetMapping()
	@RequiresPermissions("task:taskApplyInfo:taskApplyInfo")
	String TaskApplyInfo(){
	    return "task/taskApplyInfo/taskApplyInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("task:taskApplyInfo:taskApplyInfo")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<TaskApplyInfoDO> taskApplyInfoList = taskApplyInfoService.list(query);
		int total = taskApplyInfoService.count(query);
		PageUtils pageUtils = new PageUtils(taskApplyInfoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("task:taskApplyInfo:add")
	String add(){
	    return "task/taskApplyInfo/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("task:taskApplyInfo:edit")
	String edit(@PathVariable("id") Long id,Model model){
		TaskApplyInfoDO taskApplyInfo = taskApplyInfoService.get(id);
		model.addAttribute("taskApplyInfo", taskApplyInfo);
	    return "task/taskApplyInfo/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("task:taskApplyInfo:add")
	public R save( TaskApplyInfoDO taskApplyInfo){
		if(taskApplyInfoService.save(taskApplyInfo)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("task:taskApplyInfo:edit")
	public R update( TaskApplyInfoDO taskApplyInfo){
		taskApplyInfoService.update(taskApplyInfo);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("task:taskApplyInfo:remove")
	public R remove( Long id){
		if(taskApplyInfoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("task:taskApplyInfo:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		taskApplyInfoService.batchRemove(ids);
		return R.ok();
	}
	
}
