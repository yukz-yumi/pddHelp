package com.yukz.daodaoping.user.controller;

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
import com.yukz.daodaoping.user.domain.UserVsExAccountDO;
import com.yukz.daodaoping.user.service.UserVsExAccountService;

/**
 * 用户与外部账号映射表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-29 11:09:24
 */
 
@Controller
@RequestMapping("/user/userVsExAccount")
public class UserVsExAccountController {
	@Autowired
	private UserVsExAccountService userVsExAccountService;
	
	@GetMapping()
	@RequiresPermissions("user:userVsExAccount:userVsExAccount")
	String UserVsExAccount(){
	    return "user/userVsExAccount/userVsExAccount";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("user:userVsExAccount:userVsExAccount")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<UserVsExAccountDO> userVsExAccountList = userVsExAccountService.list(query);
		int total = userVsExAccountService.count(query);
		PageUtils pageUtils = new PageUtils(userVsExAccountList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("user:userVsExAccount:add")
	String add(){
	    return "user/userVsExAccount/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("user:userVsExAccount:edit")
	String edit(@PathVariable("id") Long id,Model model){
		UserVsExAccountDO userVsExAccount = userVsExAccountService.get(id);
		model.addAttribute("userVsExAccount", userVsExAccount);
	    return "user/userVsExAccount/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("user:userVsExAccount:add")
	public R save( UserVsExAccountDO userVsExAccount){
		if(userVsExAccountService.save(userVsExAccount)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("user:userVsExAccount:edit")
	public R update( UserVsExAccountDO userVsExAccount){
		userVsExAccountService.update(userVsExAccount);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("user:userVsExAccount:remove")
	public R remove( Long id){
		if(userVsExAccountService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("user:userVsExAccount:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		userVsExAccountService.batchRemove(ids);
		return R.ok();
	}
	
}
