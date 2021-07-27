package com.yukz.daodaoping.invitation.controller;

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
import com.yukz.daodaoping.invitation.domain.InvitationDO;
import com.yukz.daodaoping.invitation.service.InvitationService;

/**
 * 邀请表
 * 
 * @author micezhao
 * @email 1992lcg@163.com
 * @date 2021-07-27 07:56:37
 */
 
@Controller
@RequestMapping("/invitation/invitation")
public class InvitationController {
	@Autowired
	private InvitationService invitationService;
	
	@GetMapping()
	@RequiresPermissions("invitation:invitation:invitation")
	String Invitation(){
	    return "invitation/invitation/invitation";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("invitation:invitation:invitation")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<InvitationDO> invitationList = invitationService.list(query);
		int total = invitationService.count(query);
		PageUtils pageUtils = new PageUtils(invitationList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("invitation:invitation:add")
	String add(){
	    return "invitation/invitation/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("invitation:invitation:edit")
	String edit(@PathVariable("id") Long id,Model model){
		InvitationDO invitation = invitationService.get(id);
		model.addAttribute("invitation", invitation);
	    return "invitation/invitation/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("invitation:invitation:add")
	public R save( InvitationDO invitation){
		if(invitationService.save(invitation)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("invitation:invitation:edit")
	public R update( InvitationDO invitation){
		invitationService.update(invitation);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("invitation:invitation:remove")
	public R remove( Long id){
		if(invitationService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("invitation:invitation:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		invitationService.batchRemove(ids);
		return R.ok();
	}
	
}
