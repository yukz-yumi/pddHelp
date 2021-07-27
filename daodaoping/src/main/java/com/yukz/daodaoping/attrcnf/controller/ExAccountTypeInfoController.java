package com.yukz.daodaoping.attrcnf.controller;

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

import com.yukz.daodaoping.attrcnf.domain.ExAccountTypeInfoDO;
import com.yukz.daodaoping.attrcnf.service.ExAccountTypeInfoService;
import com.yukz.daodaoping.common.utils.PageUtils;
import com.yukz.daodaoping.common.utils.Query;
import com.yukz.daodaoping.common.utils.R;

/**
 * 外部账号类型表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 07:52:47
 */
 
@Controller
@RequestMapping("/attrcnf/exAccountTypeInfo")
public class ExAccountTypeInfoController {
	@Autowired
	private ExAccountTypeInfoService exAccountTypeInfoService;
	
	@GetMapping()
	@RequiresPermissions("attrcnf:exAccountTypeInfo:exAccountTypeInfo")
	String ExAccountTypeInfo(){
	    return "attrcnf/exAccountTypeInfo/exAccountTypeInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("attrcnf:exAccountTypeInfo:exAccountTypeInfo")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ExAccountTypeInfoDO> exAccountTypeInfoList = exAccountTypeInfoService.list(query);
		int total = exAccountTypeInfoService.count(query);
		PageUtils pageUtils = new PageUtils(exAccountTypeInfoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("attrcnf:exAccountTypeInfo:add")
	String add(){
	    return "attrcnf/exAccountTypeInfo/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("attrcnf:exAccountTypeInfo:edit")
	String edit(@PathVariable("id") Long id,Model model){
		ExAccountTypeInfoDO exAccountTypeInfo = exAccountTypeInfoService.get(id);
		model.addAttribute("exAccountTypeInfo", exAccountTypeInfo);
	    return "attrcnf/exAccountTypeInfo/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("attrcnf:exAccountTypeInfo:add")
	public R save( ExAccountTypeInfoDO exAccountTypeInfo){
		if(exAccountTypeInfoService.save(exAccountTypeInfo)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("attrcnf:exAccountTypeInfo:edit")
	public R update( ExAccountTypeInfoDO exAccountTypeInfo){
		exAccountTypeInfoService.update(exAccountTypeInfo);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("attrcnf:exAccountTypeInfo:remove")
	public R remove( Long id){
		if(exAccountTypeInfoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("attrcnf:exAccountTypeInfo:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		exAccountTypeInfoService.batchRemove(ids);
		return R.ok();
	}
	
}
