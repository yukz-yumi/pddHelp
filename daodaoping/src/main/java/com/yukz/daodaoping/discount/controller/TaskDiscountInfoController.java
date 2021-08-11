package com.yukz.daodaoping.discount.controller;

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
import com.yukz.daodaoping.discount.domain.TaskDiscountInfoDO;
import com.yukz.daodaoping.discount.service.TaskDiscountInfoService;

/**
 * 任务折扣表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-10 13:20:03
 */
 
@Controller
@RequestMapping("/discount/taskDiscountInfo")
public class TaskDiscountInfoController {
	@Autowired
	private TaskDiscountInfoService taskDiscountInfoService;
	
	@GetMapping()
	@RequiresPermissions("discount:taskDiscountInfo:taskDiscountInfo")
	String TaskDiscountInfo(){
	    return "discount/taskDiscountInfo/taskDiscountInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("discount:taskDiscountInfo:taskDiscountInfo")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<TaskDiscountInfoDO> taskDiscountInfoList = taskDiscountInfoService.list(query);
		int total = taskDiscountInfoService.count(query);
		PageUtils pageUtils = new PageUtils(taskDiscountInfoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("discount:taskDiscountInfo:add")
	String add(){
	    return "discount/taskDiscountInfo/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("discount:taskDiscountInfo:edit")
	String edit(@PathVariable("id") Long id,Model model){
		TaskDiscountInfoDO taskDiscountInfo = taskDiscountInfoService.get(id);
		model.addAttribute("taskDiscountInfo", taskDiscountInfo);
	    return "discount/taskDiscountInfo/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("discount:taskDiscountInfo:add")
	public R save( TaskDiscountInfoDO taskDiscountInfo){
		if(taskDiscountInfoService.save(taskDiscountInfo)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("discount:taskDiscountInfo:edit")
	public R update( TaskDiscountInfoDO taskDiscountInfo){
		taskDiscountInfoService.update(taskDiscountInfo);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("discount:taskDiscountInfo:remove")
	public R remove( Long id){
		if(taskDiscountInfoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("discount:taskDiscountInfo:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		taskDiscountInfoService.batchRemove(ids);
		return R.ok();
	}
	
}
