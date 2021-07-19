package com.bootdo.system.controller;

import com.bootdo.common.annotation.Log;
import com.bootdo.common.config.Constant;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.domain.Tree;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.system.domain.DeptDO;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.DeptService;
import com.xuxueli.poi.excel.ExcelExportUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门管理
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-27 14:40:36
 */

@Controller
@RequestMapping("/system/sysDept")
public class DeptController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(DeptController.class);
	private String prefix = "system/dept";
	@Autowired
	private DeptService sysDeptService;

	@GetMapping()
	@RequiresPermissions("system:sysDept:sysDept")
	String dept() {
		return prefix + "/dept";
	}

	@ApiOperation(value="获取部门列表", notes="")
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:sysDept:sysDept")
	public List<DeptDO> list() {
		Map<String, Object> query = new HashMap<>(16);
		//获取当前登陆用户及所属部门Ids
		UserDO userDO = getUser();
		if (null != userDO) {
			Long deptId = userDO.getDeptId();
			// 递归获取当前用户全部子部门id列表
			List<Long> deptIdsList = sysDeptService.listChildrenDeptIds(deptId);
			// 将当前登陆用户本身的部门也加入到列表中
			deptIdsList.add(deptId);
			query.put("deptIds", deptIdsList);
		}
		List<DeptDO> sysDeptList = sysDeptService.list(query);
		//前台显示树形列表根结点必须parentId为0或者null
		//  故没有根结点时，当前登录人的部门则为根结点部门
		int currIndex = 0;
		boolean hasRoot = false;
		for (int i=0; i<sysDeptList.size(); i++) {
			DeptDO dept = sysDeptList.get(i);
			// 记录下当前登录人的部门index
			if (dept.getDeptId() == userDO.getDeptId()) {
				currIndex = i;
			}
			//如果存在根结点则退出循环，否则继续遍历
			if (null==dept.getParentId() || dept.getParentId()==0){
				hasRoot = true;
				break;
			}
		}
		// 如果没有根结点，则需要添加根结点
		if (!hasRoot) {
			DeptDO currDept = sysDeptList.get(currIndex);
			currDept.setParentId(0l);
		}
		return sysDeptList;
	}

	@GetMapping("/add/{pId}")
	@RequiresPermissions("system:sysDept:add")
	String add(@PathVariable("pId") Long pId, Model model) {
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", "总部门");
		} else {
			DeptDO parent = sysDeptService.get(pId);
			model.addAttribute("pName", parent.getName());
			model.addAttribute("segmentCode", parent.getSegmentCode());
		}
		return  prefix + "/add";
	}

	@GetMapping("/edit/{deptId}")
	@RequiresPermissions("system:sysDept:edit")
	String edit(@PathVariable("deptId") Long deptId, Model model) {
		DeptDO sysDept = sysDeptService.get(deptId);
		model.addAttribute("sysDept", sysDept);
		if(Constant.DEPT_ROOT_ID.equals(sysDept.getParentId())) {
			model.addAttribute("parentDeptName", "无");
		}else {
			DeptDO parDept = sysDeptService.get(sysDept.getParentId());
			model.addAttribute("parentDeptName", parDept.getName());
		}
		return  prefix + "/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:sysDept:add")
	public R save(DeptDO sysDept) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		//获取父部门的segmentCode，并保存到当前部门中
		if (null != sysDept.getParentId() && 0 != sysDept.getParentId()){
			DeptDO parentDept = sysDeptService.get(sysDept.getParentId());
			sysDept.setSegmentCode(parentDept.getSegmentCode());
		}
		if (sysDeptService.save(sysDept) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:sysDept:edit")
	public R update(DeptDO sysDept) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (sysDeptService.update(sysDept) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("system:sysDept:remove")
	public R remove(Long deptId) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", deptId);
		if(sysDeptService.count(map)>0) {
			return R.error(1, "包含下级部门,不允许修改");
		}
		if(sysDeptService.checkDeptHasUser(deptId)) {
			if (sysDeptService.remove(deptId) > 0) {
				return R.ok();
			}
		}else {
			return R.error(1, "部门包含用户,不允许修改");
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:sysDept:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] deptIds) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		sysDeptService.batchRemove(deptIds);
		return R.ok();
	}

	@GetMapping("/tree")
	@ResponseBody
	public Tree<DeptDO> tree() {
		Tree<DeptDO> tree = new Tree<DeptDO>();
		tree = sysDeptService.getTree();
		return tree;
	}

	@GetMapping("/treeByPid")
	@ResponseBody
	public Tree<DeptDO> treeByPid() {
		Tree<DeptDO> tree = new Tree<DeptDO>();
		String pid = this.getUser().getDeptId().toString();
		tree = sysDeptService.getTreeByPId(pid);
		return tree;
	}

	@GetMapping("/treeByPidAndSelf")
	@ResponseBody
	public Tree<DeptDO> treeByPidAndSelf() {
		Tree<DeptDO> tree = new Tree<DeptDO>();
		String pid = this.getUser().getDeptId().toString();
		// 部门id=0代表是超级管理员admin，超级管理员是系统预置，部门编号初始化为0
		if (StringUtils.equals("0",pid)){
			tree = sysDeptService.getTree();
		} else {
			tree = sysDeptService.getTreeByPIdAndSelf(pid);
		}
		return tree;
	}

	@GetMapping("/treeView")
	String treeView() {
		return  prefix + "/deptTree";
	}

	/**
	 * 导出
	 * @param request
	 * @param response
	 * @author
	 * @return
	 * @throws Exception
	 */
	@Log("导出部门")
	@RequestMapping(value="/exportExcel")
	public void exportExcel(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String filename = "部门导出"+format.format(new Date().getTime())+".xls";
		response.setContentType("application/ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename="+new String(filename.getBytes(),"iso-8859-1"));
		OutputStream out = response.getOutputStream();
		try {
			Query query = new Query(params);
			String type = request.getParameter("type");
			//导出当前页面数据
			if(type.equals("1")){
				List<DeptDO> deptDOs = sysDeptService.list(query);
				if(null != deptDOs && deptDOs.size()>0) {
					Workbook workbook = ExcelExportUtil.exportWorkbook(deptDOs);
					workbook.write(out);
				}
			}
			//导出全部数据
			if(type.equals("2")){
				List<DeptDO> deptDOs = sysDeptService.list(null);
				if(null != deptDOs && deptDOs.size()>0) {
					Workbook workbook = ExcelExportUtil.exportWorkbook(deptDOs);
					workbook.write(out);
				}
			}
			//导出符合条件的全部数据
			if(type.equals("3")){
				query.remove("offset");
				query.remove("limit");
				List<DeptDO> deptDOs = sysDeptService.list(query);
				if(null != deptDOs && deptDOs.size()>0) {
					Workbook workbook = ExcelExportUtil.exportWorkbook(deptDOs);
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
