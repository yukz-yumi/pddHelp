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
import com.yukz.daodaoping.fund.domain.FundTransferInfoDO;
import com.yukz.daodaoping.fund.service.FundTransferInfoService;

/**
 * 资金流水表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 07:51:04
 */
 
@Controller
@RequestMapping("/fund/fundTransferInfo")
public class FundTransferInfoController {
	@Autowired
	private FundTransferInfoService fundTransferInfoService;
	
	@GetMapping()
	@RequiresPermissions("fund:fundTransferInfo:fundTransferInfo")
	String FundTransferInfo(){
	    return "fund/fundTransferInfo/fundTransferInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("fund:fundTransferInfo:fundTransferInfo")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<FundTransferInfoDO> fundTransferInfoList = fundTransferInfoService.list(query);
		int total = fundTransferInfoService.count(query);
		PageUtils pageUtils = new PageUtils(fundTransferInfoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("fund:fundTransferInfo:add")
	String add(){
	    return "fund/fundTransferInfo/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("fund:fundTransferInfo:edit")
	String edit(@PathVariable("id") Long id,Model model){
		FundTransferInfoDO fundTransferInfo = fundTransferInfoService.get(id);
		model.addAttribute("fundTransferInfo", fundTransferInfo);
	    return "fund/fundTransferInfo/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("fund:fundTransferInfo:add")
	public R save( FundTransferInfoDO fundTransferInfo){
		if(fundTransferInfoService.save(fundTransferInfo)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("fund:fundTransferInfo:edit")
	public R update( FundTransferInfoDO fundTransferInfo){
		fundTransferInfoService.update(fundTransferInfo);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("fund:fundTransferInfo:remove")
	public R remove( Long id){
		if(fundTransferInfoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("fund:fundTransferInfo:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		fundTransferInfoService.batchRemove(ids);
		return R.ok();
	}
	
}
