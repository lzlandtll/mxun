CREATE DATABASE `mxun-mem`;

use `mxun-mem`;

-- `mxun-mem`.mem_user definition

CREATE TABLE `mem_user` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
`article_count` bigint NOT NULL DEFAULT '0' COMMENT '编写文章总数',
`article_like_count` bigint NOT NULL DEFAULT '0' COMMENT '文章总点赞数',
`article_collection_count` bigint NOT NULL DEFAULT '0' COMMENT '文章总的收藏数',
`follow_count` bigint NOT NULL DEFAULT '0' COMMENT '粉丝数量',
`create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
`create_by` varchar(50) DEFAULT NULL COMMENT '创建人ID',
`update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
`update_by` varchar(50) DEFAULT NULL COMMENT '修改用户ID',
`is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标志(0: 未删除, 1: 已删除, 默认为0)',
`version` int DEFAULT '1' COMMENT '版本号',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;