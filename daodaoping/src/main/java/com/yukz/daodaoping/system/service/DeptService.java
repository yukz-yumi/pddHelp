package com.yukz.daodaoping.system.service;

import com.yukz.daodaoping.common.domain.Tree;
import com.yukz.daodaoping.system.domain.DeptDO;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-27 14:28:36
 */
public interface DeptService {
	
	DeptDO get(Long deptId);
	
	List<DeptDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DeptDO sysDept);
	
	int update(DeptDO sysDept);
	
	int remove(Long deptId);
	
	int batchRemove(Long[] deptIds);

	Tree<DeptDO> getTree();
	Tree<DeptDO> getTreeByPId(String pid);
	Tree<DeptDO> getTreeByPIdAndSelf(String pid);

	boolean checkDeptHasUser(Long deptId);

	List<Long> listChildrenIds(Long parentId);

	List<Long> listChildrenDeptIds(Long parentId);

	List<DeptDO> listChildrenAndSelf(Long parentId);

	List<DeptDO> listAllParent(Long selfId);

	DeptDO getTopParentDept(Long deptId);
}
