package com.yukz.daodaoping.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.yukz.daodaoping.common.aspect.CleanData;
import com.yukz.daodaoping.common.config.ConfigKey;
import com.yukz.daodaoping.common.config.DataRedisKey;
import com.yukz.daodaoping.system.config.RedisHandler;
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
	@Autowired
	private RedisHandler redisHandler;
	
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
	@CleanData(DataRedisKey.REDIS_USER_INFO_DATA)//清除redis缓存
	public R save( UserInfoDO userInfo){
		if(userInfoService.save(userInfo)>0){
			setRedis();
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
	@CleanData(DataRedisKey.REDIS_USER_INFO_DATA)//清除redis缓存
	public R update( UserInfoDO userInfo){
		userInfoService.update(userInfo);
		setRedis();
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("user:userInfo:remove")
	@CleanData(DataRedisKey.REDIS_USER_INFO_DATA)//清除redis缓存
	public R remove( Long id){
		if(userInfoService.remove(id)>0){
			setRedis();
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
	@CleanData(DataRedisKey.REDIS_USER_INFO_DATA)//清除redis缓存
	public R remove(@RequestParam("ids[]") Long[] ids){
		userInfoService.batchRemove(ids);
		setRedis();
		return R.ok();
	}

	private void setRedis() {
		Long agentId = ConfigKey.agentId;
		//同步最新数据到redis
		Map<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("agentId", agentId);
		List<UserInfoDO> userInfoList = userInfoService.list(mapQuery);
		String dataJson = JSON.toJSONString(userInfoList);
		this.redisHandler.set(DataRedisKey.REDIS_USER_INFO_DATA+agentId, dataJson, 30L, TimeUnit.DAYS);
	}
}
