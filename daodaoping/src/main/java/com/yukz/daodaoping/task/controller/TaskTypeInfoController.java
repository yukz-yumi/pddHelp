package com.yukz.daodaoping.task.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yukz.daodaoping.common.config.ConfigKey;
import com.yukz.daodaoping.common.controller.BaseController;
import com.yukz.daodaoping.task.enums.IsAllowEnum;
import com.yukz.daodaoping.task.enums.PlatformEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.redisson.client.codec.BaseCodec;
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
import com.yukz.daodaoping.task.domain.TaskTypeInfoDO;
import com.yukz.daodaoping.task.service.TaskTypeInfoService;

/**
 * 任务类型表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-08-02 09:04:24
 */
 
@Controller
@RequestMapping("/task/taskTypeInfo")
public class TaskTypeInfoController extends BaseController {
	@Autowired
	private TaskTypeInfoService taskTypeInfoService;
	
	@GetMapping()
	@RequiresPermissions("task:taskTypeInfo:taskTypeInfo")
	String TaskTypeInfo(Model model){
		model.addAttribute("isAllowList", IsAllowEnum.toList());
		model.addAttribute("platformList", PlatformEnum.toList());
	    return "task/taskTypeInfo/taskTypeInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("task:taskTypeInfo:taskTypeInfo")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<TaskTypeInfoDO> taskTypeInfoList = taskTypeInfoService.list(query);
		int total = taskTypeInfoService.count(query);
		//给图片地址加上url前缀,价格分转换成元
		for (TaskTypeInfoDO taskType : taskTypeInfoList) {
			taskType.setTaskImg(ConfigKey.imgUrl+taskType.getTaskImg());
			Integer price = taskType.getPrice();
			BigDecimal priceBD = new BigDecimal(price);
			BigDecimal priceView = priceBD.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
			taskType.setPriceYuan(priceView);
		}
		PageUtils pageUtils = new PageUtils(taskTypeInfoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("task:taskTypeInfo:add")
	String add(Model model){
		model.addAttribute("platformList", PlatformEnum.toList());
	    return "task/taskTypeInfo/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("task:taskTypeInfo:edit")
	String edit(@PathVariable("id") Long id,Model model){
		TaskTypeInfoDO taskTypeInfo = taskTypeInfoService.get(id);
		Integer priceInt = taskTypeInfo.getPrice();
		BigDecimal priceView = new BigDecimal(priceInt)
				.divide(new BigDecimal(100))
				.setScale(2, BigDecimal.ROUND_HALF_UP);
		taskTypeInfo.setPriceYuan(priceView);
		model.addAttribute("taskTypeInfo", taskTypeInfo);
		model.addAttribute("platformList", PlatformEnum.toList());
		model.addAttribute("picServerUrl", ConfigKey.imgUrl);
	    return "task/taskTypeInfo/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("task:taskTypeInfo:add")
	public R save( TaskTypeInfoDO taskTypeInfo){
		Date now = new Date();
		taskTypeInfo.setGmtCreate(now);
		taskTypeInfo.setGmtModify(now);
		taskTypeInfo.setAgentId(ConfigKey.agentId);
		String allowed = taskTypeInfo.getAllowed();
		if (StringUtils.equals(allowed, IsAllowEnum.YES.getStatus())) {
			taskTypeInfo.setAllowed(allowed);
		} else {
			taskTypeInfo.setAllowed(IsAllowEnum.NO.getStatus());
		}
		//价格处理成分为单位
		BigDecimal priceInt = taskTypeInfo.getPriceYuan();
		taskTypeInfo.setPrice(priceInt.multiply(new BigDecimal(100)).intValue());
		if(taskTypeInfoService.save(taskTypeInfo)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("task:taskTypeInfo:edit")
	public R update( TaskTypeInfoDO taskTypeInfo){
		Date now = new Date();
		taskTypeInfo.setGmtModify(now);
		String allowed = taskTypeInfo.getAllowed();
		if (StringUtils.equals(allowed, IsAllowEnum.YES.getStatus())) {
			taskTypeInfo.setAllowed(allowed);
		} else {
			taskTypeInfo.setAllowed(IsAllowEnum.NO.getStatus());
		}
		//价格处理成分为单位
		BigDecimal priceInt = taskTypeInfo.getPriceYuan();
		taskTypeInfo.setPrice(priceInt.multiply(new BigDecimal(100)).intValue());
		taskTypeInfoService.update(taskTypeInfo);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("task:taskTypeInfo:remove")
	public R remove( Long id){
		if(taskTypeInfoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("task:taskTypeInfo:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		taskTypeInfoService.batchRemove(ids);
		return R.ok();
	}
	
}
