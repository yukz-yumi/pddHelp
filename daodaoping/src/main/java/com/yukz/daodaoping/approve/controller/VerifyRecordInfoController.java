package com.yukz.daodaoping.approve.controller;

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

import com.yukz.daodaoping.approve.domain.VerifyRecordInfoDO;
import com.yukz.daodaoping.approve.service.VerifyRecordInfoService;
import com.yukz.daodaoping.common.utils.PageUtils;
import com.yukz.daodaoping.common.utils.Query;
import com.yukz.daodaoping.common.utils.R;

/**
 * 任务审核记录表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 08:09:51
 */
 
@Controller
@RequestMapping("/approve/verifyRecordInfo")
public class VerifyRecordInfoController {
	@Autowired
	private VerifyRecordInfoService verifyRecordInfoService;
	
	@GetMapping()
	@RequiresPermissions("approve:verifyRecordInfo:verifyRecordInfo")
	String VerifyRecordInfo(){
	    return "approve/verifyRecordInfo/verifyRecordInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("approve:verifyRecordInfo:verifyRecordInfo")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<VerifyRecordInfoDO> verifyRecordInfoList = verifyRecordInfoService.list(query);
		int total = verifyRecordInfoService.count(query);
		PageUtils pageUtils = new PageUtils(verifyRecordInfoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("approve:verifyRecordInfo:add")
	String add(){
	    return "approve/verifyRecordInfo/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("approve:verifyRecordInfo:edit")
	String edit(@PathVariable("id") Long id,Model model){
		VerifyRecordInfoDO verifyRecordInfo = verifyRecordInfoService.get(id);
		model.addAttribute("verifyRecordInfo", verifyRecordInfo);
	    return "approve/verifyRecordInfo/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("approve:verifyRecordInfo:add")
	public R save( VerifyRecordInfoDO verifyRecordInfo){
		if(verifyRecordInfoService.save(verifyRecordInfo)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("approve:verifyRecordInfo:edit")
	public R update( VerifyRecordInfoDO verifyRecordInfo){
		verifyRecordInfoService.update(verifyRecordInfo);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("approve:verifyRecordInfo:remove")
	public R remove( Long id){
		if(verifyRecordInfoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("approve:verifyRecordInfo:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		verifyRecordInfoService.batchRemove(ids);
		return R.ok();
	}
	
}
