package com.yukz.daodaoping.order.controller;

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
import com.yukz.daodaoping.order.domain.OrderInfoDO;
import com.yukz.daodaoping.order.service.OrderInfoService;

/**
 * 订单表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 08:03:58
 */
 
@Controller
@RequestMapping("/order/orderInfo")
public class OrderInfoController {
	@Autowired
	private OrderInfoService orderInfoService;
	
	@GetMapping()
	@RequiresPermissions("order:orderInfo:orderInfo")
	String OrderInfo(){
	    return "order/orderInfo/orderInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("order:orderInfo:orderInfo")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<OrderInfoDO> orderInfoList = orderInfoService.list(query);
		int total = orderInfoService.count(query);
		PageUtils pageUtils = new PageUtils(orderInfoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("order:orderInfo:add")
	String add(){
	    return "order/orderInfo/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("order:orderInfo:edit")
	String edit(@PathVariable("id") Long id,Model model){
		OrderInfoDO orderInfo = orderInfoService.get(id);
		model.addAttribute("orderInfo", orderInfo);
	    return "order/orderInfo/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("order:orderInfo:add")
	public R save( OrderInfoDO orderInfo){
		if(orderInfoService.save(orderInfo)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("order:orderInfo:edit")
	public R update( OrderInfoDO orderInfo){
		orderInfoService.update(orderInfo);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("order:orderInfo:remove")
	public R remove( Long id){
		if(orderInfoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("order:orderInfo:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		orderInfoService.batchRemove(ids);
		return R.ok();
	}
	
}
