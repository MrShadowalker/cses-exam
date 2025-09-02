CREATE database if NOT EXISTS `cses` default character set utf8mb4 collate utf8mb4_unicode_ci;
USE `cses`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- --------------
-- 配置相关
-- --------------

-- 分数组合排名配置
DROP TABLE IF EXISTS `t_rank_config`;
CREATE TABLE `t_rank_config`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `rank` int NOT NULL comment '排名',
  `title` VARCHAR(50) NULL DEFAULT NULL comment '称号',
  `score_combination` VARCHAR(50) NOT NULL comment '分数组合',
  `combination_hash` VARCHAR(32) NOT NULL comment '分数组合哈希值，用于快速查询',
  `total_score` int NOT NULL comment '组合总分',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_combination`(`score_combination`) USING BTREE,
  INDEX `idx_combination_hash`(`combination_hash`) USING BTREE,
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- 痛点配置
DROP TABLE IF EXISTS `t_pain_point`;
CREATE TABLE `t_pain_point`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `subject` int NULL DEFAULT NULL comment '环节',
  `subject_name` varchar(32) NULL DEFAULT NULL comment '环节名称',
  `level` int NULL DEFAULT NULL comment '层级',
  `name` varchar(32) NULL DEFAULT NULL comment '名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL comment '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

DROP TABLE IF EXISTS `t_painpoint_config`;
CREATE TABLE `t_painpoint_config`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `subject` int NULL DEFAULT NULL comment '环节',
  `subject_name` varchar(32) NULL DEFAULT NULL comment '环节名称',
  `level` int NULL DEFAULT NULL comment '层级',
  `painpoint` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL comment '痛点',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- TODO 抽题配置



-- --------------
-- 数据
-- --------------

DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_uuid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  `sex` int NULL DEFAULT NULL,
  `birth_day` datetime NULL DEFAULT NULL,
  `user_level` int NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role` int NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `image_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `modify_time` datetime NULL DEFAULT NULL,
  `last_active_time` datetime NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  `wx_open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;
INSERT INTO `t_user` VALUES (1, '52045f5f-a13f-4ccc-93dd-f7ee8270ad4c', 'admin', 'D1AGFL+Gx37t0NPG4d6biYP5Z31cNbwhK5w1lUeiHB2zagqbk8efYfSjYoh1Z/j1dkiRjHU+b0EpwzCh8IGsksJjzD65ci5LsnodQVf4Uj6D3pwoscXGqmkjjpzvSJbx42swwNTA+QoDU8YLo7JhtbUK2X0qCjFGpd+8eJ5BGvk=', '管理员', 30, 1, '2019-09-07 18:56:07', NULL, NULL, 3, 1, NULL, '2019-09-07 18:56:21', NULL, NULL, b'0', NULL);
INSERT INTO `t_user` VALUES (2, 'd2d29da2-dcb3-4013-b874-727626236f47', 'student', 'D1AGFL+Gx37t0NPG4d6biYP5Z31cNbwhK5w1lUeiHB2zagqbk8efYfSjYoh1Z/j1dkiRjHU+b0EpwzCh8IGsksJjzD65ci5LsnodQVf4Uj6D3pwoscXGqmkjjpzvSJbx42swwNTA+QoDU8YLo7JhtbUK2X0qCjFGpd+8eJ5BGvk=', '学生', 18, 1, '2019-09-01 16:00:00', 1, '19171171610', 1, 1, 'https://www.mindskip.net:9008/image/ba607a75-83ba-4530-8e23-660b72dc4953/头像.jpg', '2019-09-07 18:55:02', '2020-02-04 08:26:54', NULL, b'0', NULL);


DROP TABLE IF EXISTS `t_user_token`;
CREATE TABLE `t_user_token`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `token` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  `wx_open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `end_time` datetime NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- 试题表
DROP TABLE IF EXISTS `t_question`;
CREATE TABLE `t_question`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `target_type` varchar(32) NULL DEFAULT NULL comment '目标类型',
  `subject` int NOT NULL comment '环节',
  `scene` varchar(32) NULL DEFAULT NULL comment '场景',
  `weight` decimal(10,3) NULL DEFAULT NULL comment '题目算分权重',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL comment '题目内容',
  `create_by` int NULL DEFAULT NULL comment '创建人',
  `create_time` datetime NULL DEFAULT NULL comment '创建时间',
  `update_by` int NULL DEFAULT NULL comment '修改人',
  `update_time` datetime NULL DEFAULT NULL comment '修改时间',
  `deleted` bit(1) NULL DEFAULT NULL comment '删除状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT COMMENT '题目表';

-- 试题选项表
DROP TABLE IF EXISTS `t_question_option`;
CREATE TABLE `t_question_option`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `question_id` int NULL DEFAULT NULL comment '题目id',
  `level` int NULL DEFAULT NULL comment '选项等级',
  `score` int NULL DEFAULT NULL comment '选项得分',
  `order` int NULL DEFAULT NULL comment '选项排序',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL comment '选项内容',
  `create_by` int NULL DEFAULT NULL comment '创建人',
  `create_time` datetime NULL DEFAULT NULL comment '创建时间',
  `update_by` int NULL DEFAULT NULL comment '修改人',
  `update_time` datetime NULL DEFAULT NULL comment '修改时间',
  `deleted` bit(1) NULL DEFAULT NULL comment '删除状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT COMMENT '题目选项表';

-- 用户试卷表
DROP TABLE IF EXISTS `t_exam_paper`;
CREATE TABLE `t_exam_paper`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL comment '用户id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL comment '试卷标题',
  `version` varchar(32) NULL DEFAULT NULL comment '版本',
  `origin_max_score_combination` varchar(32) NULL DEFAULT NULL comment '上限原始得分组合',
  `max_score_combination` varchar(32) NULL DEFAULT NULL comment '上限得分组合',
  `max_rank` int NULL DEFAULT NULL comment '上限排名',
  `origin_normal_score_combination` varchar(32) NULL DEFAULT NULL comment '常态原始得分组合',
  `normal_score_combination` varchar(32) NULL DEFAULT NULL comment '常态得分组合',
  `normal_rank` int NULL DEFAULT NULL comment '常态排名',
  `create_time` datetime NULL DEFAULT NULL comment '创建时间',
  `update_time` datetime NULL DEFAULT NULL comment '更新时间',
  `deleted` bit(1) NULL DEFAULT NULL comment '删除状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT COMMENT '用户试卷表';

-- 试卷题目关联表
DROP TABLE IF EXISTS `t_exam_paper_question`;
CREATE TABLE `t_exam_paper_question`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `exam_paper_id` int NULL DEFAULT NULL comment '试卷id',
  `question_id` int NULL DEFAULT NULL comment '题目id',
  `order` int NULL DEFAULT NULL comment '题号',
  `option_order` varchar(50) NULL DEFAULT NULL comment '选项排序',
  `create_by` int NULL DEFAULT NULL comment '创建人',
  `create_time` datetime NULL DEFAULT NULL comment '创建时间',
  `update_by` int NULL DEFAULT NULL comment '修改人',
  `update_time` datetime NULL DEFAULT NULL comment '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_paper_question`(`exam_paper_id`, `question_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT COMMENT '用户试卷题目关联表';

-- 用户试卷题目答案表
DROP TABLE IF EXISTS `t_exam_paper_question_answer`;
CREATE TABLE `t_exam_paper_question_answer`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `exam_paper_id` int NULL DEFAULT NULL comment '试卷id',
  `question_id` int NULL DEFAULT NULL comment '题目id',
  `selected_option_ids` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL comment '全部选项id，逗号分隔',
  `normal_option_id` int NULL DEFAULT NULL comment '常态选项id',
  `max_score` int NULL DEFAULT NULL comment '上限得分',
  `normal_score` int NULL DEFAULT NULL comment '常态得分',
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT COMMENT '用户试卷题目答案表';


-- 用户测试解读报告表
DROP TABLE IF EXISTS `t_exam_paper_report`;
CREATE TABLE `t_exam_paper_report`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `exam_paper_id` int NULL DEFAULT NULL comment '试卷id',
  `user_id` int NULL DEFAULT NULL comment '用户id',
  `version` varchar(32) NULL DEFAULT NULL comment '版本',
  `normal_rank` int NULL DEFAULT NULL comment '常态排名',
  `max_rank` int NULL DEFAULT NULL comment '上限排名',
  `max_rank_description`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL comment '上限排名描述',
  `advantage` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL comment '优势',
  `need_improvement` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL comment '待提升点',
  `bottleneck` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL comment '瓶颈',
  `synergy_analysis` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL comment '整体协同性分析',
  `probable_painpoints` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL comment '大概率存在的痛点',
  `possible_painpoints` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL comment '可能存在的痛点',
  `probable_painpoints_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL comment '大概率存在的痛点描述',
  `possible_painpoints_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL comment '可能存在的痛点描述',
  `create_time` datetime NULL DEFAULT NULL comment '创建时间',
  `update_time` datetime NULL DEFAULT NULL comment '更新时间',
  `deleted` bit(1) NULL DEFAULT NULL comment '删除状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT COMMENT '用户测试解读报告表';


-- --------------
-- 其他
-- --------------

-- 消息表
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `send_user_id` int NULL DEFAULT NULL,
  `send_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `send_real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `receive_user_count` int NULL DEFAULT NULL,
  `read_count` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- 消息用户关联表
DROP TABLE IF EXISTS `t_message_user`;
CREATE TABLE `t_message_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `message_id` int NULL DEFAULT NULL,
  `receive_user_id` int NULL DEFAULT NULL,
  `receive_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `receive_real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `readed` bit(1) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `read_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- 用户事件日志表
DROP TABLE IF EXISTS `t_user_event_log`;
CREATE TABLE `t_user_event_log`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;


SET FOREIGN_KEY_CHECKS = 1;
