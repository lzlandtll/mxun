package com.mxun.art.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import com.mybatisflex.core.activerecord.Model;

import java.io.Serial;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文章评论关联表 实体类。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@Accessors(chain = true)
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
@Table("art_comment")
public class Comment extends Model<Comment> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 评论ID
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 文章ID,关联mxun-art.art_article.id字段
     */
    private Long articleId;

    /**
     * 评论者ID,关联mxun-sys.sys_user.id
     */
    private Long userId;

    /**
     * 父评论ID,关联mxun-art.art_comment.id字段,如果为-1表示是直接评论文章
     */
    private Long parentId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人ID,关联mxun-sys.sys_user.id
     */
    private String createBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 修改用户ID,关联mxun-sys.sys_user.id
     */
    private String updateBy;

    /**
     * 逻辑删除标志(0: 未删除, 1: 已删除, 默认为0)
     */
    private Boolean isDeleted;

    /**
     * 版本号
     */
    private Integer version;

}
