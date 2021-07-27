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

import com.yukz.daodaoping.attrcnf.domain.AccountAttrDO;
import com.yukz.daodaoping.attrcnf.service.AccountAttrService;
import com.yukz.daodaoping.common.utils.PageUtils;
import com.yukz.daodaoping.common.utils.Query;
import com.yukz.daodaoping.common.utils.R;

/**
 * 账号属性配置表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 07:52:47
 */
 
@Controller
@RequestMapping("/attrcnf/accountAttr")
public class AccountAttrController {
	@Autowired
	private AccountAttrService accountAttrService;
	
	@GetMapping()
	@RequiresPermissions("attrcnf:accountAttr:accountAttr")
	String AccountAttr(){
	    return "attrcnf/accountAttr/accountAttr";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("attrcnf:accountAttr:accountAttr")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<AccountAttrDO> accountAttrList = accountAttrService.list(query);
		int total = accountAttrService.count(query);
		PageUtils pageUtils = new PageUtils(accountAttrList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("attrcnf:accountAttr:add")
	String add(){
	    return "attrcnf/accountAttr/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("attrcnf:accountAttr:edit")
	String edit(@PathVariable("id") Long id,Model model){
		AccountAttrDO accountAttr = accountAttrService.get(id);
		model.addAttribute("accountAttr", accountAttr);
	    return "attrcnf/accountAttr/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("attrcnf:accountAttr:add")
	public R save( AccountAttrDO accountAttr){
		if(accountAttrService.save(accountAttr)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("attrcnf:accountAttr:edit")
	public R update( AccountAttrDO accountAttr){
		accountAttrService.update(accountAttr);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("attrcnf:accountAttr:remove")
	public R remove( Long id){
		if(accountAttrService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("attrcnf:accountAttr:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		accountAttrService.batchRemove(ids);
		return R.ok();
	}
	
}
