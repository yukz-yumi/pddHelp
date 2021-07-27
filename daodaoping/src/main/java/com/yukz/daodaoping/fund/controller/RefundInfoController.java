package com.yukz.daodaoping.fund.controller;

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
import com.yukz.daodaoping.fund.domain.RefundInfoDO;
import com.yukz.daodaoping.fund.service.RefundInfoService;

/**
 * 退款记录表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 07:51:04
 */
 
@Controller
@RequestMapping("/fund/refundInfo")
public class RefundInfoController {
	@Autowired
	private RefundInfoService refundInfoService;
	
	@GetMapping()
	@RequiresPermissions("fund:refundInfo:refundInfo")
	String RefundInfo(){
	    return "fund/refundInfo/refundInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("fund:refundInfo:refundInfo")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<RefundInfoDO> refundInfoList = refundInfoService.list(query);
		int total = refundInfoService.count(query);
		PageUtils pageUtils = new PageUtils(refundInfoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("fund:refundInfo:add")
	String add(){
	    return "fund/refundInfo/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("fund:refundInfo:edit")
	String edit(@PathVariable("id") Long id,Model model){
		RefundInfoDO refundInfo = refundInfoService.get(id);
		model.addAttribute("refundInfo", refundInfo);
	    return "fund/refundInfo/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("fund:refundInfo:add")
	public R save( RefundInfoDO refundInfo){
		if(refundInfoService.save(refundInfo)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("fund:refundInfo:edit")
	public R update( RefundInfoDO refundInfo){
		refundInfoService.update(refundInfo);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("fund:refundInfo:remove")
	public R remove( Long id){
		if(refundInfoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("fund:refundInfo:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		refundInfoService.batchRemove(ids);
		return R.ok();
	}
	
}
