package com.yukz.daodaoping.discount.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yukz.daodaoping.discount.domain.DiscountConfigDO;
import com.yukz.daodaoping.discount.service.DiscountConfigService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 折扣配置表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-10 13:20:03
 */
 
@Controller
@RequestMapping("/discount/discountConfig")
public class DiscountConfigController {
	@Autowired
	private DiscountConfigService discountConfigService;
	
	@GetMapping()
	@RequiresPermissions("discount:discountConfig:discountConfig")
	String DiscountConfig(){
	    return "discount/discountConfig/discountConfig";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("discount:discountConfig:discountConfig")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<DiscountConfigDO> discountConfigList = discountConfigService.list(query);
		int total = discountConfigService.count(query);
		PageUtils pageUtils = new PageUtils(discountConfigList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("discount:discountConfig:add")
	String add(){
	    return "discount/discountConfig/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("discount:discountConfig:edit")
	String edit(@PathVariable("id") Long id,Model model){
		DiscountConfigDO discountConfig = discountConfigService.get(id);
		model.addAttribute("discountConfig", discountConfig);
	    return "discount/discountConfig/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("discount:discountConfig:add")
	public R save( DiscountConfigDO discountConfig){
		if(discountConfigService.save(discountConfig)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("discount:discountConfig:edit")
	public R update( DiscountConfigDO discountConfig){
		discountConfigService.update(discountConfig);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("discount:discountConfig:remove")
	public R remove( Long id){
		if(discountConfigService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("discount:discountConfig:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		discountConfigService.batchRemove(ids);
		return R.ok();
	}
	
}
