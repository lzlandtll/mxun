package com.mxun.art.entity;

import com.mxun.common.core.CommonEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import com.mybatisflex.core.activerecord.Model;

import java.io.Serial;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 文章历史记录(草稿)表，用户编辑完成之后可以进行发布 实体类。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@NoArgsConstructor
@Accessors(chain = true)
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
@Table("art_article_history")
public class ArticleHistory extends CommonEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文章ID，关联到mxun-art.art_article.id
     */
    private Long articleId;

    /**
     * 文章标题
     */
    @NotBlank(message = "05009")
    private String title;

    /**
     * 文章简述
     */
    private String summary;

    /**
     * 文章内容
     */
    @NotBlank(message = "05008")
    private String content;

    /**
     * 用户分类ID,关联mxun-art.art_category.id
     */
    private Integer categoryId;

    /**
     * md5值,快速判断是否有更改
     */
    private String md5;

    /**
     * 保存状态: 01:自动保存、02:手动保存、03:手动发布
     */
    private String saveStatus;

    /**
     * 文章草稿状态: 01:草稿、02:待审核、03:审核通过、04:审核未通过
     */
    private String status;

}
