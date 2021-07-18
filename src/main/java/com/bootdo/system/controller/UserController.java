package com.bootdo.system.controller;

import com.bootdo.common.annotation.Log;
import com.bootdo.common.config.Constant;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.domain.FileDO;
import com.bootdo.common.domain.Tree;
import com.bootdo.common.service.DictService;
import com.bootdo.common.utils.*;
import com.bootdo.gotoo.controller.WorkOrderController;
import com.bootdo.gotoo.domain.WorkOrderDO;
import com.bootdo.system.domain.DeptDO;
import com.bootdo.system.domain.RoleDO;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.DeptService;
import com.bootdo.system.service.RoleService;
import com.bootdo.system.service.UserService;
import com.bootdo.system.vo.UserVO;
import javax.servlet.http.HttpServletRequest;

import com.xuxueli.poi.excel.ExcelExportUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/sys/user")
@Controller
public class UserController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private String prefix="system/user"  ;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	DictService dictService;
	@Autowired
	DeptService deptService;
	@RequiresPermissions("sys:user:user")
	@GetMapping("")
	String user(Model model) {
		return prefix + "/user";
	}

	@GetMapping("/list")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		//获取当前登陆用户及所属部门Ids
		UserDO userDO = getUser();
		if (null != userDO) {
			Long deptId = userDO.getDeptId();
			// 递归获取当前用户全部子部门id列表
			List<Long> deptIdsList = deptService.listChildrenDeptIds(deptId);
			// 将当前登陆用户本身的部门也加入到列表中
			deptIdsList.add(deptId);
			query.put("deptIds", deptIdsList);
		}
		List<UserDO> sysUserList = userService.list(query);
		int total = userService.count(query);
		PageUtils pageUtil = new PageUtils(sysUserList, total);
		return pageUtil;
	}

	@RequiresPermissions("sys:user:add")
	@Log("添加用户")
	@GetMapping("/add")
	String add(Model model) {
		//只查询登陆用户所属的客户编码的数据
		Map<String, Object> queryMap = new HashMap<String, Object>();
		String segmentCode = getSegmentCode();
		//  admin可以查询所有数据，故除admin外其他用户都只能查看自己所属segmentCode的数据
		if (!StringUtils.equals(segmentCode, "0")) {
			queryMap.put("segmentCode", getSegmentCode());
		}
		List<RoleDO> roles = roleService.list(queryMap);
		model.addAttribute("roles", roles);
		return prefix + "/add";
	}

	@RequiresPermissions("sys:user:edit")
	@Log("编辑用户")
	@GetMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") Long id) {
		UserDO userDO = userService.get(id);
		model.addAttribute("user", userDO);

		//只查询登陆用户所属的客户编码的数据
		Map<String, Object> queryMap = new HashMap<String, Object>();
		String segmentCode = getSegmentCode();
		//  admin可以查询所有数据，故除admin外其他用户都只能查看自己所属segmentCode的数据
		if (!StringUtils.equals(segmentCode, "0")) {
			queryMap.put("segmentCode", getSegmentCode());
		}
		List<RoleDO> roles = roleService.list(queryMap);
		model.addAttribute("roles", roles);

		return prefix+"/edit";
	}

	@RequiresPermissions("sys:user:add")
	@Log("保存用户")
	@PostMapping("/save")
	@ResponseBody
	R save(UserDO user) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
		//根据用户所属部门获取段级编码，并保存到用户表中
		DeptDO segmentDept = deptService.get(user.getDeptId());
		if (null != segmentDept) {
			user.setSegmentCode(segmentDept.getSegmentCode());
		}
		if (userService.save(user) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@RequiresPermissions("sys:user:edit")
	@Log("更新用户")
	@PostMapping("/update")
	@ResponseBody
	R update(UserDO user) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		//根据用户所属部门获取段级编码，并保存到用户表中
		if(null != user.getDeptId()) {
			DeptDO segmentDept = deptService.get(user.getDeptId());
			if (null != segmentDept) {
				user.setSegmentCode(segmentDept.getSegmentCode());
			}
		}
		if (userService.update(user) > 0) {
			return R.ok();
		}
		return R.error();
	}


	@RequiresPermissions("sys:user:edit")
	@Log("更新用户")
	@PostMapping("/updatePeronal")
	@ResponseBody
	R updatePeronal(UserDO user) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		//根据用户所属部门获取段级编码，并保存到用户表中
		if(null != user.getDeptId()) {
			DeptDO segmentDept = deptService.get(user.getDeptId());
			if (null != segmentDept) {
				user.setSegmentCode(segmentDept.getSegmentCode());
			}
		}
		if (userService.updatePersonal(user) > 0) {
			return R.ok();
		}
		return R.error();
	}


	@RequiresPermissions("sys:user:remove")
	@Log("删除用户")
	@PostMapping("/remove")
	@ResponseBody
	R remove(Long id) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (userService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@RequiresPermissions("sys:user:batchRemove")
	@Log("批量删除用户")
	@PostMapping("/batchRemove")
	@ResponseBody
	R batchRemove(@RequestParam("ids[]") Long[] userIds) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		int r = userService.batchremove(userIds);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}

	@PostMapping("/exit")
	@ResponseBody
	boolean exit(@RequestParam Map<String, Object> params) {
		// 存在，不通过，false
		return !userService.exit(params);
	}

	@RequiresPermissions("sys:user:resetPwd")
	@Log("请求更改用户密码")
	@GetMapping("/resetPwd/{id}")
	String resetPwd(@PathVariable("id") Long userId, Model model) {

		UserDO userDO = new UserDO();
		userDO.setUserId(userId);
		model.addAttribute("user", userDO);
		return prefix + "/reset_pwd";
	}

	@Log("提交更改用户密码")
	@PostMapping("/resetPwd")
	@ResponseBody
	R resetPwd(UserVO userVO) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		try{
			userService.resetPwd(userVO,getUser());
			return R.ok();
		}catch (Exception e){
			return R.error(1,e.getMessage());
		}

	}
	@RequiresPermissions("sys:user:resetPwd")
	@Log("admin提交更改用户密码")
	@PostMapping("/adminResetPwd")
	@ResponseBody
	R adminResetPwd(UserVO userVO) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		try{
			userService.adminResetPwd(userVO);
			return R.ok();
		}catch (Exception e){
			return R.error(1,e.getMessage());
		}

	}
	@GetMapping("/tree")
	@ResponseBody
	public Tree<DeptDO> tree() {
		Tree<DeptDO> tree = new Tree<DeptDO>();
		tree = userService.getTree();
		return tree;
	}

	@GetMapping("/treeView")
	String treeView() {
		return  prefix + "/userTree";
	}

	@GetMapping("/personal")
	String personal(Model model) {
		UserDO userDO  = userService.get(getUserId());
		model.addAttribute("user",userDO);
		model.addAttribute("hobbyList",dictService.getHobbyList(userDO));
		model.addAttribute("sexList",dictService.getSexList());
		return prefix + "/personal";
	}
	@ResponseBody
	@PostMapping("/uploadImg")
	R uploadImg(@RequestParam("avatar_file") MultipartFile file, String avatar_data, HttpServletRequest request) {
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		Map<String, Object> result = new HashMap<>();
		try {
			result = userService.updatePersonalImg(file, avatar_data, getUserId());
		} catch (Exception e) {
			return R.error("更新图像失败！");
		}
		if(result!=null && result.size()>0){
			return R.ok(result);
		}else {
			return R.error("更新图像失败！");
		}
	}

	/**
	 * 导出
	 * @param request
	 * @param response
	 * @author
	 * @return
	 * @throws Exception
	 */
	@Log("导出用户")
	@RequestMapping(value="/exportExcel")
	public void exportExcel(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String filename = "派工单导出"+format.format(new Date().getTime())+".xls";
		response.setContentType("application/ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename="+new String(filename.getBytes(),"iso-8859-1"));
		OutputStream out = response.getOutputStream();
		try {
			Query query = new Query(params);
			String type = request.getParameter("type");
			//导出当前页面数据
			if(type.equals("1")){
				List<UserDO> userDOs = userService.list(query);
				if(null != userDOs && userDOs.size()>0) {
					Workbook workbook = ExcelExportUtil.exportWorkbook(userDOs);
					workbook.write(out);
				}
			}
			//导出全部数据
			if(type.equals("2")){
				List<UserDO> userDOs = userService.list(null);
				if(null != userDOs && userDOs.size()>0) {
					Workbook workbook = ExcelExportUtil.exportWorkbook(userDOs);
					workbook.write(out);
				}
			}
			//导出符合条件的全部数据
			if(type.equals("3")){
				query.remove("offset");
				query.remove("limit");
				List<UserDO> userDOs = userService.list(query);
				if(null != userDOs && userDOs.size()>0) {
					Workbook workbook = ExcelExportUtil.exportWorkbook(userDOs);
					workbook.write(out);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("exportExcel出错"+e.getMessage());
		}finally{
			out.close();
		}
	}
}
