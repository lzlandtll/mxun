package com.mxun.art.entity;

import com.mxun.common.core.CommonEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import com.mybatisflex.core.activerecord.Model;

import java.io.Serial;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 文章评论关联表 实体类。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@NoArgsConstructor
@Accessors(chain = true)
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
@Table("art_comment")
public class Comment extends CommonEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 805321021L;

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

}
