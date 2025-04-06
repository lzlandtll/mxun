CREATE DATABASE `mxun-art`;

use `mxun-art`;

-- `mxun-art`.art_article definition
CREATE TABLE `art_article` (
                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT '文章ID',
                               `title` varchar(255) NOT NULL COMMENT '文章标题',
                               `summary` varchar(255) DEFAULT NULL COMMENT '文章简述',
                               `author_id` bigint NOT NULL COMMENT '作者ID,关联mxun-sys.sys_user.id',
                               `content` text NOT NULL COMMENT '文章内容',
                               `category_id` bigint DEFAULT NULL COMMENT '用户分类ID,关联mxun-art.art_category.id',
                               `like_count` int DEFAULT '0' COMMENT '点赞数',
                               `collection_count` int DEFAULT '0' COMMENT '收藏数',
                               `view_count` int DEFAULT '0' COMMENT '浏览次数',
                               `status` char(2) DEFAULT NULL COMMENT '状态(01:保存、02:待审核、03:审核通过、04:审核未通过)',
                               `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                               `create_by` varchar(50) DEFAULT NULL COMMENT '创建人ID,关联mxun-sys.sys_user.id',
                               `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
                               `update_by` varchar(50) DEFAULT NULL COMMENT '修改用户ID,关联mxun-sys.sys_user.id',
                               `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志(0: 未删除, 1: 已删除, 默认为0)',
                               `version` int DEFAULT '1' COMMENT '版本号',
                               PRIMARY KEY (`id`),
                               KEY `art_article_author_id_IDX` (`author_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章表,存储已经发布成功的文章信息';

-- `mxun-art`.art_article_favorite definition
CREATE TABLE `art_article_favorite` (
                                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                        `article_id` bigint NOT NULL COMMENT '文章ID,关联mxun-art.art_article.id字段',
                                        `favorite_user_id` bigint NOT NULL COMMENT '用户ID,关联mxun-sys.sys_user.id',
                                        `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                                        `create_by` varchar(50) DEFAULT NULL COMMENT '创建人ID,关联mxun-sys.sys_user.id',
                                        `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
                                        `update_by` varchar(50) DEFAULT NULL COMMENT '修改用户ID,关联mxun-sys.sys_user.id',
                                        `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志(0: 未删除, 1: 已删除, 默认为0)',
                                        `version` int DEFAULT '1' COMMENT '版本号',
                                        PRIMARY KEY (`id`),
                                        KEY `art_article_collection_article_id_IDX` (`article_id`) USING BTREE,
                                        KEY `art_article_collection_collection_user_id_IDX` (`favorite_user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章收藏表';

-- `mxun-art`.art_article_history definition
CREATE TABLE `art_article_history` (
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '文章历史记录ID',
                                       `article_id` bigint NOT NULL COMMENT '文章ID，关联到mxun-art.art_article.id',
                                       `title` varchar(255) DEFAULT NULL COMMENT '文章标题',
                                       `summary` varchar(255) DEFAULT NULL COMMENT '文章简述',
                                       `content` text NOT NULL COMMENT '文章内容',
                                       `category_id` bigint DEFAULT NULL COMMENT '用户分类ID,关联mxun-art.art_category.id',
                                       `md5` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'md5值,快速判断是否有更改',
                                       `save_status` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '保存状态: 01:自动保存、02:手动保存、03:手动发布',
                                       `status` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '01' COMMENT '文章草稿状态: 01:保存、02:待审核、03:审核通过、04:审核未通过',
                                       `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                                       `create_by` varchar(50) DEFAULT NULL COMMENT '创建人ID,关联mxun-sys.sys_user.id',
                                       `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
                                       `update_by` varchar(50) DEFAULT NULL COMMENT '修改用户ID,关联mxun-sys.sys_user.id',
                                       `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志(0: 未删除, 1: 已删除, 默认为0)',
                                       `version` int DEFAULT '1' COMMENT '版本号',
                                       PRIMARY KEY (`id`),
                                       KEY `art_article_history_article_id_IDX` (`article_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章历史记录(草稿)表，用户编辑完成之后可以进行发布';

-- `mxun-art`.art_article_like definition
CREATE TABLE `art_article_like` (
                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '点赞ID',
                                    `article_id` bigint NOT NULL COMMENT '文章ID,关联mxun-art.art_article.id字段',
                                    `like_user_id` bigint NOT NULL COMMENT '用户ID,关联mxun-sys.sys_user.id',
                                    `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                                    `create_by` varchar(50) DEFAULT NULL COMMENT '创建人ID,关联mxun-sys.sys_user.id',
                                    `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
                                    `update_by` varchar(50) DEFAULT NULL COMMENT '修改用户ID,关联mxun-sys.sys_user.id',
                                    `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志(0: 未删除, 1: 已删除, 默认为0)',
                                    `version` int DEFAULT '1' COMMENT '版本号',
                                    PRIMARY KEY (`id`),
                                    KEY `art_article_like_article_id_IDX` (`article_id`) USING BTREE,
                                    KEY `art_article_like_like_user_id_IDX` (`like_user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章点赞表';

-- `mxun-art`.art_article_tag definition
CREATE TABLE `art_article_tag` (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签ID',
                                   `article_id` bigint NOT NULL COMMENT '文章ID,关联到mxun-art.art_article.id字段',
                                   `tag_id` bigint NOT NULL COMMENT '标签ID,关联到mxun-art.art_tag.id字段',
                                   `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                                   `create_by` varchar(50) DEFAULT NULL COMMENT '创建人ID,关联mxun-sys.sys_user.id',
                                   `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
                                   `update_by` varchar(50) DEFAULT NULL COMMENT '修改用户ID,关联mxun-sys.sys_user.id',
                                   `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志(0: 未删除, 1: 已删除, 默认为0)',
                                   `version` int DEFAULT '1' COMMENT '版本号',
                                   PRIMARY KEY (`id`),
                                   KEY `art_article_tag_tag_id_IDX` (`tag_id`) USING BTREE,
                                   KEY `art_article_tag_article_id_IDX` (`article_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章标签关联表';

-- `mxun-art`.art_category definition
CREATE TABLE `art_category` (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '集合ID',
                                `user_id` bigint NOT NULL COMMENT '用户ID, 关联mxun-sys.sys_user.id',
                                `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
                                `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '分类描述',
                                `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                                `create_by` varchar(50) DEFAULT NULL COMMENT '创建人ID,关联mxun-sys.sys_user.id',
                                `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
                                `update_by` varchar(50) DEFAULT NULL COMMENT '修改用户ID,关联mxun-sys.sys_user.id',
                                `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志(0: 未删除, 1: 已删除, 默认为0)',
                                `version` int DEFAULT '1' COMMENT '版本号',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户创建的分类表';

-- `mxun-art`.art_comment definition
CREATE TABLE `art_comment` (
                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评论ID',
                               `article_id` bigint NOT NULL COMMENT '文章ID,关联mxun-art.art_article.id字段',
                               `user_id` bigint NOT NULL COMMENT '评论者ID,关联mxun-sys.sys_user.id',
                               `parent_id` bigint DEFAULT '-1' COMMENT '父评论ID,关联mxun-art.art_comment.id字段,如果为-1表示是直接评论文章',
                               `content` text NOT NULL COMMENT '评论内容',
                               `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                               `create_by` varchar(50) DEFAULT NULL COMMENT '创建人ID,关联mxun-sys.sys_user.id',
                               `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
                               `update_by` varchar(50) DEFAULT NULL COMMENT '修改用户ID,关联mxun-sys.sys_user.id',
                               `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志(0: 未删除, 1: 已删除, 默认为0)',
                               `version` int DEFAULT '1' COMMENT '版本号',
                               PRIMARY KEY (`id`),
                               KEY `art_comment_article_id_IDX` (`article_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章评论关联表';

-- `mxun-art`.art_tag definition
CREATE TABLE `art_tag` (
                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签ID',
                           `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名称',
                           `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                           `create_by` varchar(50) DEFAULT NULL COMMENT '创建人ID,关联mxun-sys.sys_user.id',
                           `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
                           `update_by` varchar(50) DEFAULT NULL COMMENT '修改用户ID,关联mxun-sys.sys_user.id',
                           `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志(0: 未删除, 1: 已删除, 默认为0)',
                           `version` int DEFAULT '1' COMMENT '版本号',
                           PRIMARY KEY (`id`),
                           KEY `art_tag_tag_name_IDX` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='关于文章的标签表';
INSERT INTO `mxun-art`.art_tag
(id, name, create_time, create_by, update_time, update_by, is_deleted, version)
VALUES(1, 'Java编程', '2025-04-01 06:45:13.052000000', NULL, '2025-04-01 06:48:25.064000000', NULL, 0, 1);
INSERT INTO `mxun-art`.art_tag
(id, name, create_time, create_by, update_time, update_by, is_deleted, version)
VALUES(2, 'Python编程', '2025-04-01 06:45:13.052000000', NULL, '2025-04-01 06:45:13.052000000', NULL, 0, 1);

