use daodaoping_dev;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ddp_user_info`
-- ----------------------------
DROP TABLE IF EXISTS `ddp_user_info`;
CREATE TABLE `ddp_user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户编号',
  `open_id` varchar(128) NOT NULL COMMENT '用户openid',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '用户微信昵称',
  `head_img_url` varchar(1024) DEFAULT NULL COMMENT '用户头像',
  `scores` bigint(20)  DEFAULT '0'  COMMENT '用户当前积分',
  `user_grade` varchar(64) DEFAULT NULL COMMENT '用户等级',
  `mobile` varchar(32) DEFAULT NULL COMMENT '用户联系方式',
  `user_status` varchar(16) DEFAULT 'unverified' COMMENT '用户状态:unverified 未认证/ verified 已认证/ forbidden 已停用',
  `agent_id` bigint(8)  NOT NULL COMMENT '机构编号',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户信息表';


-- ----------------------------
-- Table structure for `ddp_user_vs_ex_account`
-- ----------------------------
DROP TABLE IF EXISTS `ddp_user_vs_ex_account`;
CREATE TABLE `ddp_user_vs_ex_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户编号',
  `account_type` varchar(32) NOT NULL COMMENT '账号类型',
  `account` varchar(128) NOT NULL COMMENT '账号',
  `allowed` varchar(16) DEFAULT 'yes' COMMENT '用户是否启用: yes/no',
  `account_status` varchar(16) DEFAULT 'available' COMMENT '系统为当前账号设定的状态 available/forbidden',
  `account_img` varchar(16) NOT NULL COMMENT '账号截图图片地址',
  `agent_id` bigint(8)  NOT NULL COMMENT '机构编号',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户与外部账号映射表';

-- ----------------------------
-- Table structure for `ddp_ex_account_type_info`
-- ----------------------------
DROP TABLE IF EXISTS `ddp_ex_account_type_info`;
CREATE TABLE `ddp_ex_account_type_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_type` varchar(32) NOT NULL COMMENT '账号类型',
  `platform` varchar(32) NOT NULL COMMENT '平台类型',
  `type_desc` varchar(128) NOT NULL COMMENT '账号类型说明',
  `valid_url` varchar(2048) DEFAULT NULL COMMENT '账户验证地址',
  `agent_id` bigint(8)  NOT NULL COMMENT '机构编号',
  `allowed` varchar(16) DEFAULT 'yes' COMMENT '是否启用: yes/no',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='外部账号类型表';

-- ----------------------------
-- Table structure for `ddp_task_apply_info`
-- ----------------------------
DROP TABLE IF EXISTS `ddp_task_apply_info`;
CREATE TABLE `ddp_task_apply_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` varchar(128) NOT NULL COMMENT '任务编号',
  `task_type_id` varchar(128) NOT NULL COMMENT '任务类型id',
  `assistant_type` varchar(128) NOT NULL COMMENT '助力方式',
  `command` varchar(2048) DEFAULT NULL COMMENT '任务指令',
  `task_number` int  NOT NULL COMMENT '任务数量',
  `completed_numeber` int  DEFAULT NULL COMMENT '已完成数量',
  `task_result` varchar(16) DEFAULT NULL COMMENT '任务结果 uncompleted/completed',
  `task_status` varchar(16) DEFAULT 'suspend' COMMENT '任务状态 suspend/wait/pending/end/cancel',
  `start_time` datetime DEFAULT NULL COMMENT '任务开启时间',
  `expire_time` datetime DEFAULT NULL COMMENT '任务过期时间',
  `agent_id` bigint(8)  NOT NULL COMMENT '机构编号',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='任务申请表';

-- ----------------------------
-- Table structure for `ddp_task_type_info`
-- ----------------------------
DROP TABLE IF EXISTS `ddp_task_type_info`;
CREATE TABLE `ddp_task_type_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `platform` varchar(32) NOT NULL COMMENT '平台类型',
  `task_type_desc` varchar(256) NOT NULL COMMENT '任务类型说明',
  `task_type` varchar(128) NOT NULL COMMENT '助力方式',
  `allowed` varchar(16) DEFAULT 'yes' COMMENT '是否启用: yes/no',
  `agent_id` bigint(8)  NOT NULL COMMENT '机构编号',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='任务类型表';


-- ----------------------------
-- Table structure for `ddp_order_info`
-- ----------------------------
DROP TABLE IF EXISTS `ddp_order_info`;
CREATE TABLE `ddp_order_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL COMMENT '订单编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `task_id` bigint(20) NOT NULL COMMENT '任务id',
  `total_amount` bigint(20) NOT NULL COMMENT '订单应付',
  `scores_deduction` bigint(20) DEFAULT '0' COMMENT '积分扣减',
  `discount` int(2) NOT NULL COMMENT '折扣',
  `payment_amount` bigint(20) NOT NULL COMMENT '实际支付订单金额',
  `order_status` char(2) NOT NULL COMMENT '0:未支付/1:支付中/2:已支付/3:已过期/4:已取消',
  `expire_time` datetime  NOT NULL COMMENT '订单支付过期时间',
  `agent_id` bigint(8)  NOT NULL COMMENT '机构编号',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='订单表';



-- ----------------------------
-- Table structure for `ddp_fund_transfer_info`
-- ----------------------------
DROP TABLE IF EXISTS `ddp_fund_transfer_info`;
CREATE TABLE `ddp_fund_transfer_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL COMMENT '订单编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `open_id` varchar(128) NOT NULL COMMENT '用户openid',
  `biz_type` varchar(16) NOT NULL COMMENT '业务类型',
  `trans_type` varchar(32) NOT NULL COMMENT '流水类型',
  `fund_amount` bigint(20) NOT NULL COMMENT '交易金额',
  `trans_status` varchar(16) NOT NULL COMMENT '交易状态',
  `err_msg` varchar(2048) DEFAULT NULL COMMENT '失败原因',
  `agent_id` bigint(8)  NOT NULL COMMENT '机构编号',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='资金流水表';


-- ----------------------------
-- Table structure for `ddp_refund_info`
-- ----------------------------
DROP TABLE IF EXISTS `ddp_refund_info`;
CREATE TABLE `ddp_refund_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `refund_id` bigint(20) NOT NULL COMMENT '退费id',
  `task_id` bigint(20) NOT NULL COMMENT '退费任务id',
  `refund_amount` bigint(20) NOT NULL COMMENT '退费金额',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `open_id` varchar(128) NOT NULL COMMENT '用户openid',
  `refund_status` varchar(16) NOT NULL COMMENT '交易状态:wait:等待/success:成功/fail:失败',
  `err_msg` varchar(2048) DEFAULT NULL COMMENT '失败原因',
  `agent_id` bigint(8)  NOT NULL COMMENT '机构编号',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='退款记录表';



-- ----------------------------
-- Table structure for `ddp_task_accept_info`
-- ----------------------------
DROP TABLE IF EXISTS `ddp_task_accept_info`;
CREATE TABLE `ddp_task_accept_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) NOT NULL COMMENT '退费任务id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `open_id` varchar(128) NOT NULL COMMENT '用户openid',
  `certification_url` varchar(2048) NOT NULL COMMENT '任务凭证',
  `task_status` varchar(16) NOT NULL COMMENT '任务状态 pending:接受执行中/expire:已过期/end:已完成',
  `verify_status` varchar(16) NOT NULL COMMENT '审核结果 verifying:审核中/unverified:未通过/verified:已审核 ',
  `worker_id` bigint(20) DEFAULT NULL COMMENT '审核人id',
  `has_paid` char DEFAULT '0' COMMENT '是否放款 0:未放款/1:已放款',
  `agent_id` bigint(8)  NOT NULL COMMENT '机构编号',
  `expire_time` datetime DEFAULT NULL COMMENT '任务过期时间',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='任务认领表';


-- ----------------------------
-- Table structure for `ddp_invitation`
-- ----------------------------
DROP TABLE IF EXISTS `ddp_invitation`;
CREATE TABLE `ddp_invitation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `open_id` varchar(128) DEFAULT NULL COMMENT '用户openid',
  `indirect_user_id` bigint(20) DEFAULT NULL COMMENT '间接推荐人id',
  `direct_user_id` bigint(20) DEFAULT NULL COMMENT '直接推荐人id',
  `direct_openid` varchar(64) DEFAULT NULL COMMENT '间接推荐人open_id',
  `indirect_openid` varchar(64) DEFAULT NULL COMMENT '直接推荐人open_id',
  `direct_nick_name` varchar(64) DEFAULT NULL COMMENT '直接推荐人昵称',
  `indirect_nick_name` varchar(64) DEFAULT NULL COMMENT '间接推荐人昵称',
  `agent_id` bigint(8)  DEFAULT NULL COMMENT '机构编号',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='邀请表';

-- ----------------------------
-- Table structure for `ddp_account_attr`
-- ----------------------------
DROP TABLE IF EXISTS `ddp_account_attr`;
CREATE TABLE `ddp_account_attr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ex_account_id` bigint(20) NOT NULL COMMENT '用户id',
  `attr_key` varchar(64) NOT NULL COMMENT '属性名',
  `attr_value` varchar(64) NOT NULL COMMENT '属性值',
  `agent_id` bigint(8)  NOT NULL COMMENT '机构编号',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='账号属性配置表';

-- ----------------------------
-- Table structure for `ddp_task_delay_operation_info`
-- ----------------------------
DROP TABLE IF EXISTS `ddp_task_delay_operation_info`;
CREATE TABLE `ddp_task_delay_operation_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) NOT NULL COMMENT '用户id',
  `delay_count` int NOT NULL COMMENT '延迟次数',
  `delay_time` datetime NOT NULL COMMENT '延迟时间',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `agent_id` bigint(8)  NOT NULL COMMENT '机构编号',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='任务延迟操作信息表';


-- ----------------------------
-- Table structure for `ddp_verify_record_info`
-- ----------------------------
DROP TABLE IF EXISTS `ddp_verify_record_info`;
CREATE TABLE `ddp_verify_record_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_accept_id` bigint(20) NOT NULL COMMENT '任务认领id',
  `task_id` bigint(20) NOT NULL COMMENT '任务id',
  `worker_id` bigint(20) NOT NULL COMMENT '任务id',
  `operation` varchar(64) NOT NULL COMMENT '审核动作',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `invalidate_reason` varchar(2048) DEFAULT NULL COMMENT '打回原因',
  `agent_id` bigint(8)  NOT NULL COMMENT '机构编号',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='任务审核记录表';


-- ----------------------------
-- Table structure for `ddp_feedback_info`
-- ----------------------------
DROP TABLE IF EXISTS `ddp_feedback_info`;
CREATE TABLE `ddp_feedback_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `open_id` bigint(20) NOT NULL COMMENT '用户openid',
  `e_mail` varchar(512) DEFAULT NULL COMMENT '邮箱地址',
  `feedback_type` varchar(32) NOT NULL COMMENT '反馈问题类型',
  `feedback_desc` varchar(512) NOT NULL COMMENT '反馈问题类型',
  `feedback_img_url_1` varchar(2048) DEFAULT NULL COMMENT '图片1',
  `feedback_img_url_2` varchar(2048) DEFAULT NULL COMMENT '图片2',
  `feedback_img_url_3` varchar(2048) DEFAULT NULL COMMENT '图片3',
  `feedback_img_url_4` varchar(2048) DEFAULT NULL COMMENT '图片4',
  `feedback_img_url_5` varchar(2048) DEFAULT NULL COMMENT '图片5',
  `worker_id` bigint(20) NOT NULL COMMENT '处理人id',
  `handle_resule` varchar(32) DEFAULT NULL COMMENT '处理结果',
  `feedback_time` datetime DEFAULT NULL COMMENT '反馈时间',
  `handle_time` datetime DEFAULT NULL COMMENT '处理时间',
  `agent_id` bigint(8)  NOT NULL COMMENT '机构编号',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户反馈记录';


