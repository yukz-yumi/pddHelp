package com.yukz.daodaoping.system.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yukz.daodaoping.common.domain.Tree;
import com.yukz.daodaoping.common.utils.BuildTree;
import com.yukz.daodaoping.system.dao.DeptDao;
import com.yukz.daodaoping.system.domain.DeptDO;
import com.yukz.daodaoping.system.service.DeptService;



@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao sysDeptMapper;

    @Override
    public DeptDO get(Long deptId) {
        return sysDeptMapper.get(deptId);
    }

    @Override
    public List<DeptDO> list(Map<String, Object> map) {
        return sysDeptMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return sysDeptMapper.count(map);
    }

    @Override
    public int save(DeptDO sysDept) {
        return sysDeptMapper.save(sysDept);
    }

    @Override
    public int update(DeptDO sysDept) {
        return sysDeptMapper.update(sysDept);
    }

    @Override
    public int remove(Long deptId) {
        return sysDeptMapper.remove(deptId);
    }

    @Override
    public int batchRemove(Long[] deptIds) {
        return sysDeptMapper.batchRemove(deptIds);
    }

    @Override
    public Tree<DeptDO> getTree() {
        List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
        List<DeptDO> sysDepts = sysDeptMapper.list(new HashMap<String, Object>(16));
        for (DeptDO sysDept : sysDepts) {
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(sysDept.getDeptId().toString());
            tree.setParentId(sysDept.getParentId().toString());
            tree.setText(sysDept.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<DeptDO> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public boolean checkDeptHasUser(Long deptId) {
        // TODO Auto-generated method stub
        //查询部门以及此部门的下级部门
        int result = sysDeptMapper.getDeptUserNumber(deptId);
        return result == 0 ? true : false;
    }

    @Override
    public List<Long> listChildrenIds(Long parentId) {
        List<DeptDO> deptDOS = list(null);
        return treeMenuList(deptDOS, parentId);
    }

    List<Long> treeMenuList(List<DeptDO> menuList, long pid) {
        List<Long> childIds = new ArrayList<>();
        for (DeptDO mu : menuList) {
            //遍历出父id等于参数的id，add进子节点集合
            if (mu.getParentId() == pid) {
                //递归遍历下一级
                treeMenuList(menuList, mu.getDeptId());
                childIds.add(mu.getDeptId());
            }
        }
        return childIds;
    }

    /**
     * 根据父结点查询树形结构
     * @param pid
     * @return
     */
    @Override
    public Tree<DeptDO> getTreeByPId(String pid) {
        List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
        List<DeptDO> sysDepts = listChildren(Long.parseLong(pid));
        for (DeptDO sysDept : sysDepts) {
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(sysDept.getDeptId().toString());
            tree.setParentId(sysDept.getParentId().toString());
            tree.setText(sysDept.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            tree.setState(state);
            trees.add(tree);
        }


        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<DeptDO> t = BuildTree.buildTreeByPid(trees,get(Long.parseLong(pid)));
        return t;
    }

    /**
     * 根据父结点查询树形结构包含父节点本身
     * @param pid
     * @return
     */
    @Override
    public Tree<DeptDO> getTreeByPIdAndSelf(String pid) {
        List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
        List<DeptDO> sysDepts = listChildrenAndSelf(Long.parseLong(pid));
        for (DeptDO sysDept : sysDepts) {
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(sysDept.getDeptId().toString());
            tree.setParentId(sysDept.getParentId().toString());
            tree.setText(sysDept.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            tree.setState(state);
            trees.add(tree);
        }


        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<DeptDO> t = BuildTree.buildTreeByPidAndSelf(trees,get(Long.parseLong(pid)));
        return t;
    }

    /**
     * 根据父结点查询所有子部门和本身，返回部门列表
     * @param parentId 父ID
     * @return
     */
    @Override
    public List<DeptDO> listChildrenAndSelf(Long parentId) {
        List<DeptDO> deptDOS = list(null);
        List<DeptDO> childs = new ArrayList<>();
        treeListByPid(deptDOS, parentId,childs);
        //添加自身到列表中
        DeptDO self = sysDeptMapper.get(parentId);
        childs.add(self);
        return childs;
    }

    public List<DeptDO> listChildren(Long parentId) {
        List<DeptDO> deptDOS = list(null);
        List<DeptDO> childs = new ArrayList<>();
        treeListByPid(deptDOS, parentId,childs);
        return childs;
    }

    private void treeListByPid(List<DeptDO> menuList, long pid,List<DeptDO> childs) {
        for (DeptDO mu : menuList) {
            //遍历出父id等于参数的id，add进子节点集合
            if (mu.getParentId() == pid) {
                //递归遍历下一级
                treeListByPid(menuList, mu.getDeptId(),childs);
                childs.add(mu);
            }
        }
    }


    @Override
    public List<Long> listChildrenDeptIds(Long parentId) {
        List<DeptDO> deptDOS = list(null);
        List<Long> listDeptIds = new ArrayList<>();
        return deptIdsList(deptDOS, parentId,listDeptIds);
    }

    List<Long> deptIdsList(List<DeptDO> menuList, long pid,List<Long> listDeptIds) {
        for (DeptDO mu : menuList) {
            //遍历出父id等于参数的id，add进子节点集合
            if (mu.getParentId() == pid) {
                //递归遍历下一级
                deptIdsList(menuList, mu.getDeptId(),listDeptIds);
                listDeptIds.add(mu.getDeptId());
            }
        }
        return listDeptIds;
    }

    /**
     * 获取部门的上级部门列表，直到顶级
     * @param selfId
     * @return
     */
    @Override
    public List<DeptDO> listAllParent(Long selfId) {
        List<DeptDO> deptDOS = list(null);
        DeptDO selfDept = get(selfId);
        List<DeptDO> allParentList = new ArrayList<>();
        return allParentDeptList(selfDept, deptDOS, allParentList);
    }

    List<DeptDO> allParentDeptList(DeptDO selfDept, List<DeptDO> deptList, List<DeptDO> allParentList) {
        for (DeptDO dept : deptList) {
            //找到本节点的父节点
            if (dept.getDeptId() == selfDept.getParentId()) {
                allParentList.add(dept);
                //如果父节点deptId不等于0，则不是顶级节点，需要递归继续遍历
                if (dept.getDeptId() != 0) {
                    allParentDeptList(dept, deptList, allParentList);
                } else {
                    break;
                }
            }
        }
        return allParentList;
    }

    /**
     * 获取顶级父节点
     * @param deptId
     * @return
     */
    @Override
    public DeptDO getTopParentDept(Long deptId) {
        List<DeptDO> deptDOS = list(null);
        DeptDO selfDept = get(deptId);
        return topParentDeptQuery(selfDept, deptDOS);
    }

    DeptDO topParentDeptQuery(DeptDO selfDept, List<DeptDO> deptList) {
        if (null != selfDept.getAgentId()){
            return selfDept;
        }
        for (DeptDO dept : deptList) {
            //找到本节点的父节点
            if (dept.getDeptId() == selfDept.getParentId()) {
                //如果父节点parentId不等于0，则不是顶级节点，需要递归继续遍历
                if (dept.getParentId() != 0) {
                    topParentDeptQuery(dept, deptList);
                } else {
                    return dept;
                }
            }
        }
        return null;
    }
}
