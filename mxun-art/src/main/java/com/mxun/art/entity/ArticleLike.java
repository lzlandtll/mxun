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
 * 文章点赞表 实体类。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@NoArgsConstructor
@Accessors(chain = true)
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
@Table("art_article_like")
public class ArticleLike extends CommonEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 182501447L;


    /**
     * 文章ID,关联mxun-art.art_article.id字段
     */
    private Long articleId;

    /**
     * 用户ID,关联mxun-sys.sys_user.id
     */
    private Long likeUserId;

}
