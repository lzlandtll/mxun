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
import lombok.experimental.Accessors;

/**
 * 文章标签关联表 实体类。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@Accessors(chain = true)
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
@Table("art_article_tag")
public class ArticleTag extends CommonEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文章ID,关联到mxun-art.art_article.id字段
     */
    private Long articleId;

    /**
     * 标签ID,关联到mxun-art.art_tag.id字段
     */
    private Long tagId;

}
