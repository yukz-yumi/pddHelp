package com.yukz.daodaoping.feedback.controller;

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
import com.yukz.daodaoping.feedback.domain.FeedbackInfoDO;
import com.yukz.daodaoping.feedback.service.FeedbackInfoService;

/**
 * 用户反馈记录
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 08:44:17
 */
 
@Controller
@RequestMapping("/feedback/feedbackInfo")
public class FeedbackInfoController {
	@Autowired
	private FeedbackInfoService feedbackInfoService;
	
	@GetMapping()
	@RequiresPermissions("feedback:feedbackInfo:feedbackInfo")
	String FeedbackInfo(){
	    return "feedback/feedbackInfo/feedbackInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("feedback:feedbackInfo:feedbackInfo")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<FeedbackInfoDO> feedbackInfoList = feedbackInfoService.list(query);
		int total = feedbackInfoService.count(query);
		PageUtils pageUtils = new PageUtils(feedbackInfoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("feedback:feedbackInfo:add")
	String add(){
	    return "feedback/feedbackInfo/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("feedback:feedbackInfo:edit")
	String edit(@PathVariable("id") Long id,Model model){
		FeedbackInfoDO feedbackInfo = feedbackInfoService.get(id);
		model.addAttribute("feedbackInfo", feedbackInfo);
	    return "feedback/feedbackInfo/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("feedback:feedbackInfo:add")
	public R save( FeedbackInfoDO feedbackInfo){
		if(feedbackInfoService.save(feedbackInfo)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("feedback:feedbackInfo:edit")
	public R update( FeedbackInfoDO feedbackInfo){
		feedbackInfoService.update(feedbackInfo);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("feedback:feedbackInfo:remove")
	public R remove( Long id){
		if(feedbackInfoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("feedback:feedbackInfo:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		feedbackInfoService.batchRemove(ids);
		return R.ok();
	}
	
}
