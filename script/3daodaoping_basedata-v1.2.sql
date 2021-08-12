/*
Navicat MySQL Data Transfer

Source Server         : 183.92.32.154
Source Server Version : 50640
Source Host           : 183.92.32.154:3306
Source Database       : gotoo_zz_demo

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2019-05-21 12:55:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  `segment_code` varchar(100) DEFAULT NULL COMMENT '客户编码',
  `ext1` varchar(200) DEFAULT NULL COMMENT '备用字段',
  `ext2` varchar(200) DEFAULT NULL COMMENT '备用字段',
  `ext3` varchar(200) DEFAULT NULL COMMENT '备用字段',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='部门管理';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (null, '0', '总站', '1', '1', 'wh_main', null, null, null);


-- ----------------------------
-- Records of sys_menu
-- ----------------------------



-- ----------------------------
-- Records of sys_dict
-- ----------------------------


-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `role_sign` varchar(100) DEFAULT NULL COMMENT '角色标识',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `user_id_create` bigint(255) DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '创建时间',
  `segment_code` varchar(100) DEFAULT NULL COMMENT '客户编码',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级用户角色', 'admin', '拥有最高权限', '2', '2017-08-12 00:43:52', '2017-08-12 19:14:59', 'wh_main');
INSERT INTO `sys_role` VALUES (null, '前端角色-全部', null, '前端角色-全部', null, null, null, 'wh_main');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `name` varchar(100) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `dept_id` bigint(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(255) DEFAULT NULL COMMENT '状态 0:禁用，1:正常',
  `user_id_create` bigint(255) DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `sex` bigint(32) DEFAULT NULL COMMENT '性别',
  `birth` datetime DEFAULT NULL COMMENT '出身日期',
  `pic_id` bigint(32) DEFAULT NULL,
  `live_address` varchar(500) DEFAULT NULL COMMENT '现居住地',
  `hobby` varchar(255) DEFAULT NULL COMMENT '爱好',
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  `city` varchar(255) DEFAULT NULL COMMENT '所在城市',
  `district` varchar(255) DEFAULT NULL COMMENT '所在地区',
  `segment_code` varchar(100) DEFAULT NULL COMMENT '客户编码',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (null, 'admin', '超级管理员', '27bd386e70f280e24c2f4f2a549b82cf', '0', 'aaaaa@163.com', null, '1', null, null, null, null, null, null, null, null, null, null, null, '0');



-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (null, (select user_id from `sys_user` a where a.`username`='admin'), '1');
INSERT INTO `sys_user_role` VALUES (null, (select user_id from `sys_user` a where a.`username`='zs'), (select role_id from `sys_role` b where b.`role_name`='前端角色-全部'));



-- ----------------------------
-- 系统增加多个客户使用一套系统的能力
-- 2019-12-10
-- ----------------------------
drop procedure if exists add_table_column;
create procedure add_table_column(IN v_table varchar(50), IN v_column varchar(50),IN v_column_type varchar(50))
begin
  declare stmt 		varchar(2000);
  declare v_flag 	int;
  select count(*) into v_flag from information_schema.columns where table_schema = (select DATABASE()) and table_name = v_table and column_name = v_column;
  if(v_flag=0)
  then 
	set @sqlstr = concat('ALTER TABLE `',v_table,'` ADD COLUMN `',v_column,'` ',v_column_type);
	prepare stmt from @sqlstr;
	execute stmt;
	end if;
  commit;
end;

call add_table_column('sys_dict','segment_code','varchar(100)');
call add_table_column('sys_menu','segment_code','varchar(100)');


-- ----------------------------
-- 系统菜单调整
-- ----------------------------
update `sys_menu` set name='菜单管理' where name='系统菜单';
update `sys_menu` set name='字典管理' where name='数据字典';
update `sys_menu` set parent_id='3' where name='字典管理' and parent_id='1';

update `sys_menu` set order_num='13' where name='系统管理';
update `sys_menu` set order_num='1301' where name='用户管理';
update `sys_menu` set order_num='1302' where name='角色管理';
update `sys_menu` set order_num='1303' where name='菜单管理';
update `sys_menu` set order_num='1304' where name='部门管理';
update `sys_menu` set order_num='1305' where name='字典管理';

delete from `sys_menu` where parent_id = (select x.menu_id from (select menu_id from `sys_menu` a where a.`name`='基础管理') x);
delete from `sys_menu` where name='基础管理';

delete from `sys_menu` where parent_id = (select x.menu_id from (select menu_id from `sys_menu` a where a.`name`='swagger') x);
delete from `sys_menu` where name='swagger';

delete from `sys_menu` where parent_id = (select x.menu_id from (select menu_id from `sys_menu` a where a.`name`='在线用户') x);
delete from `sys_menu` where name='在线用户';
delete from `sys_menu` where parent_id = (select x.menu_id from (select menu_id from `sys_menu` a where a.`name`='系统日志') x);
delete from `sys_menu` where name='系统日志';
delete from `sys_menu` where parent_id = (select x.menu_id from (select menu_id from `sys_menu` a where a.`name`='运行监控') x);
delete from `sys_menu` where name='运行监控';
delete from `sys_menu` where parent_id = (select x.menu_id from (select menu_id from `sys_menu` a where a.`name`='系统监控') x);
delete from `sys_menu` where name='系统监控';

delete from `sys_menu` where parent_id = (select x.menu_id from (select menu_id from `sys_menu` a where a.`name`='通知公告') x);
delete from `sys_menu` where name='通知公告';
delete from `sys_menu` where parent_id = (select x.menu_id from (select menu_id from `sys_menu` a where a.`name`='我的通知') x);
delete from `sys_menu` where name='我的通知';
delete from `sys_menu` where parent_id = (select x.menu_id from (select menu_id from `sys_menu` a where a.`name`='办公管理') x);
delete from `sys_menu` where name='办公管理';

delete from `sys_menu` where parent_id = (select x.menu_id from (select menu_id from `sys_menu` a where a.`name`='发布文章') x);
delete from `sys_menu` where name='发布文章';
delete from `sys_menu` where parent_id = (select x.menu_id from (select menu_id from `sys_menu` a where a.`name`='文章列表') x);
delete from `sys_menu` where name='文章列表';
delete from `sys_menu` where parent_id = (select x.menu_id from (select menu_id from `sys_menu` a where a.`name`='博客管理') x);
delete from `sys_menu` where name='博客管理';

delete from `sys_menu` where parent_id = (select x.menu_id from (select menu_id from `sys_menu` a where a.`name`='待办任务') x);
delete from `sys_menu` where name='待办任务';
delete from `sys_menu` where parent_id = (select x.menu_id from (select menu_id from `sys_menu` a where a.`name`='模型管理') x);
delete from `sys_menu` where name='模型管理';
delete from `sys_menu` where parent_id = (select x.menu_id from (select menu_id from `sys_menu` a where a.`name`='流程管理') x);
delete from `sys_menu` where name='流程管理';
delete from `sys_menu` where parent_id = (select x.menu_id from (select menu_id from `sys_menu` a where a.`name`='工作流程') x);
delete from `sys_menu` where name='工作流程';

delete from `sys_menu` where parent_id = (select x.menu_id from (select menu_id from `sys_menu` a where a.`name`='百度chart') x);
delete from `sys_menu` where name='百度chart';
delete from `sys_menu` where parent_id = (select x.menu_id from (select menu_id from `sys_menu` a where a.`name`='图表管理') x);
delete from `sys_menu` where name='图表管理';





-- ----------------------------
-- 删除框架多余的表
-- ----------------------------
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `act_evt_log`;
DROP TABLE IF EXISTS `act_ge_bytearray`;
DROP TABLE IF EXISTS `act_ge_property`;
DROP TABLE IF EXISTS `act_hi_actinst`;
DROP TABLE IF EXISTS `act_hi_attachment`;
DROP TABLE IF EXISTS `act_hi_comment`;
DROP TABLE IF EXISTS `act_hi_detail`;
DROP TABLE IF EXISTS `act_hi_identitylink`;
DROP TABLE IF EXISTS `act_hi_procinst`;
DROP TABLE IF EXISTS `act_hi_taskinst`;
DROP TABLE IF EXISTS `act_hi_varinst`;
DROP TABLE IF EXISTS `act_id_group`;
DROP TABLE IF EXISTS `act_id_info`;
DROP TABLE IF EXISTS `act_id_membership`;
DROP TABLE IF EXISTS `act_id_user`;
DROP TABLE IF EXISTS `act_procdef_info`;
DROP TABLE IF EXISTS `act_re_deployment`;
DROP TABLE IF EXISTS `act_re_model`;
DROP TABLE IF EXISTS `act_re_procdef`;
DROP TABLE IF EXISTS `act_ru_event_subscr`;
DROP TABLE IF EXISTS `act_ru_execution`;
DROP TABLE IF EXISTS `act_ru_identitylink`;
DROP TABLE IF EXISTS `act_ru_job`;
DROP TABLE IF EXISTS `act_ru_task`;
DROP TABLE IF EXISTS `act_ru_variable`;
DROP TABLE IF EXISTS `blog_content`;
DROP TABLE IF EXISTS `oa_notify`;
DROP TABLE IF EXISTS `oa_notify_record`;
DROP TABLE IF EXISTS `salary`;
SET FOREIGN_KEY_CHECKS = 1;


INSERT INTO `sys_menu` VALUES (106, 0, '业务系统', '', '', 0, 'fa fa-gears', 30, NULL, NULL, '100001');
INSERT INTO `sys_menu` VALUES (107, 106, '会员管理', '/user/userInfo', 'user:userInfo:userInfo', 1, '', 3001, NULL, NULL, '100001');
INSERT INTO `sys_menu` VALUES (108, 107, '新增', '', 'user:userInfo:add', 2, '', 1, NULL, NULL, '100001');
INSERT INTO `sys_menu` VALUES (109, 107, '修改', '', 'user:userInfo:edit', 2, '', 2, NULL, NULL, '100001');
INSERT INTO `sys_menu` VALUES (110, 107, '删除', '', 'user:userInfo:remove', 2, '', 3, NULL, NULL, '100001');
INSERT INTO `sys_menu` VALUES (111, 106, '任务类型管理', '/user/userInfo', 'user:userInfo:userInfo', 1, '', 3001, NULL, NULL, '100001');
INSERT INTO `sys_menu` VALUES (112, 111, '新增', '', 'user:userInfo:add', 2, '', 1, NULL, NULL, '100001');
INSERT INTO `sys_menu` VALUES (113, 111, '修改', '', 'user:userInfo:edit', 2, '', 2, NULL, NULL, '100001');
INSERT INTO `sys_menu` VALUES (114, 111, '删除', '', 'user:userInfo:remove', 2, '', 3, NULL, NULL, '100001');