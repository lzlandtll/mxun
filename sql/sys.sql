-- 创建数据库
CREATE DATABASE `mxun-sys`;

use `mxun-sys`;
-- api资源表
CREATE TABLE `sys_api_resource` (
                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                    `api_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '接口名称',
                                    `api_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '接口code,注解上根据该编码进行限制',
                                    `api_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '接口描述',
                                    `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                                    `create_by` varchar(50) DEFAULT NULL COMMENT '创建人ID',
                                    `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
                                    `update_by` varchar(50) DEFAULT NULL COMMENT '修改用户ID',
                                    `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志(0: 未删除, 1: 已删除, 默认为0)',
                                    `version` int DEFAULT '1' COMMENT '版本号',
                                    PRIMARY KEY (`id`)
) COMMENT='api资源表';
-- 插入AI资源数据
INSERT INTO `sys_api_resource` VALUES (1,'AI功能资源','CHAT_AI.CHAT_SESSION.SEND_MESSAGE','AI功能资源','2025-03-12 20:13:48.613',NULL,'2025-03-13 16:06:06.936',NULL,0,1);

-- 创建角色表
CREATE TABLE `sys_role` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `role_name` varchar(255) NOT NULL COMMENT '角色名称',
                            `role_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '角色编码',
                            `role_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色描述',
                            `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                            `create_by` varchar(50) DEFAULT NULL COMMENT '创建人ID',
                            `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
                            `update_by` varchar(50) DEFAULT NULL COMMENT '修改用户ID',
                            `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志(0: 未删除, 1: 已删除, 默认为0)',
                            `version` int DEFAULT '1' COMMENT '版本号',
                            PRIMARY KEY (`id`)
) COMMENT='角色表';
-- 插入AI角色数据
INSERT INTO `sys_role` VALUES (1,'AI功能角色','CHAT_AI','AI功能角色','2025-03-08 11:35:56.499',NULL,'2025-03-14 21:21:22.251',NULL,0,1);

-- 创建角色接口关联表
CREATE TABLE `sys_role_api` (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                `role_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色编码',
                                `api_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '接口code',
                                `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                                `create_by` varchar(50) DEFAULT NULL COMMENT '创建人ID',
                                `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
                                `update_by` varchar(50) DEFAULT NULL COMMENT '修改用户ID',
                                `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志(0: 未删除, 1: 已删除, 默认为0)',
                                `version` int DEFAULT '1' COMMENT '版本号',
                                PRIMARY KEY (`id`)
) COMMENT='角色接口关联表';
-- 插入AI角色接口关联数据
INSERT INTO `sys_role_api` VALUES (1,'CHAT_AI','CHAT_AI.CHAT_SESSION.SEND_MESSAGE','2025-03-08 19:20:27.973',NULL,'2025-03-14 22:02:39.694',NULL,0,1);

-- 创建角色用户关联表
CREATE TABLE `sys_role_user` (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                 `role_code` varchar(20) NOT NULL COMMENT '角色编码',
                                 `user_id` bigint NOT NULL COMMENT '用户id',
                                 `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                                 `create_by` varchar(50) DEFAULT NULL COMMENT '创建人ID',
                                 `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
                                 `update_by` varchar(50) DEFAULT NULL COMMENT '修改用户ID',
                                 `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志(0: 未删除, 1: 已删除, 默认为0)',
                                 `version` int DEFAULT '1' COMMENT '版本号',
                                 PRIMARY KEY (`id`)
) COMMENT='角色用户关联表';

-- 创建用户表
CREATE TABLE `sys_user` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名',
                            `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密码哈希值',
                            `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱地址',
                            `tel` varchar(15) DEFAULT NULL COMMENT '用户电话',
                            `user_type` char(2) DEFAULT NULL COMMENT '用户类型(00:超级管理员,01:后台管理员,02:普通用户,03:三方用户)',
                            `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                            `ai_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '通义千问密钥',
                            `create_by` varchar(50) DEFAULT NULL COMMENT '创建人ID',
                            `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
                            `update_by` varchar(50) DEFAULT NULL COMMENT '修改用户ID',
                            `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志(0: 未删除, 1: 已删除, 默认为0)',
                            `version` int DEFAULT '1' COMMENT '版本号',
                            PRIMARY KEY (`id`),
                            KEY `idx_username` (`username`),
                            KEY `idx_email` (`email`),
                            KEY `idx_tel` (`tel`)
) COMMENT='用户基本信息表';

