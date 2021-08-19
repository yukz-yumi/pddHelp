package com.yukz.daodaoping.system.controller;

import com.yukz.daodaoping.common.utils.PageUtils;
import com.yukz.daodaoping.common.utils.Query;
import com.yukz.daodaoping.common.utils.R;
import com.yukz.daodaoping.system.domain.SysSetDO;
import com.yukz.daodaoping.system.service.SysSetService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统设置表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-18 15:55:04
 */
 
@Controller
@RequestMapping("/system/sysSet")
public class SysSetController {
	@Autowired
	private SysSetService sysSetService;
	
	@GetMapping()
	@RequiresPermissions("system:sysSet:sysSet")
	String SysSet(){
	    return "system/sysSet/sysSet";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:sysSet:sysSet")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<SysSetDO> sysSetList = sysSetService.list(query);
		int total = sysSetService.count(query);
		PageUtils pageUtils = new PageUtils(sysSetList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:sysSet:add")
	String add(){
	    return "system/sysSet/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:sysSet:edit")
	String edit(@PathVariable("id") Long id,Model model){
		SysSetDO sysSet = sysSetService.get(id);
		//SysSetDO sysSet = sysSetService.getByKey("ttl_order", PlatformEnum.PDD.getCode(), null, 100001L);
		model.addAttribute("sysSet", sysSet);
	    return "system/sysSet/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:sysSet:add")
	public R save(SysSetDO sysSet){
		if(sysSetService.save(sysSet)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:sysSet:edit")
	public R update( SysSetDO sysSet){
		sysSetService.update(sysSet);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:sysSet:remove")
	public R remove( Long id){
		if(sysSetService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:sysSet:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		sysSetService.batchRemove(ids);
		return R.ok();
	}

}
