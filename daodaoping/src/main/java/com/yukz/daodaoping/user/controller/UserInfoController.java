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
import com.yukz.daodaoping.user.domain.UserInfoDO;
import com.yukz.daodaoping.user.service.UserInfoService;

/**
 * 用户信息表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 08:07:22
 */
 
@Controller
@RequestMapping("/user/userInfo")
public class UserInfoController {
	@Autowired
	private UserInfoService userInfoService;
	
	@GetMapping()
	@RequiresPermissions("user:userInfo:userInfo")
	String UserInfo(){
	    return "user/userInfo/userInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("user:userInfo:userInfo")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<UserInfoDO> userInfoList = userInfoService.list(query);
		int total = userInfoService.count(query);
		PageUtils pageUtils = new PageUtils(userInfoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("user:userInfo:add")
	String add(){
	    return "user/userInfo/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("user:userInfo:edit")
	String edit(@PathVariable("id") Long id,Model model){
		UserInfoDO userInfo = userInfoService.get(id);
		model.addAttribute("userInfo", userInfo);
	    return "user/userInfo/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("user:userInfo:add")
	public R save( UserInfoDO userInfo){
		if(userInfoService.save(userInfo)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("user:userInfo:edit")
	public R update( UserInfoDO userInfo){
		userInfoService.update(userInfo);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("user:userInfo:remove")
	public R remove( Long id){
		if(userInfoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("user:userInfo:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		userInfoService.batchRemove(ids);
		return R.ok();
	}
	
}
