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
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 文章表,存储已经发布成功的文章信息 实体类。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
@Table("art_article")
public class Article extends CommonEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 54589L;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章简述
     */
    private String summary;

    /**
     * 作者ID,关联mxun-sys.sys_user.id
     */
    private Long authorId;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 用户分类ID,关联mxun-art.art_category.id
     */
    private Long categoryId;

    /**
     * 点赞数
     */
    private Long likeCount;

    /**
     * 收藏数
     */
    private Long collectionCount;

    /**
     * 浏览次数
     */
    private Long viewCount;

    /**
     * 评论次数
     */
    private Long commentCount;

    /**
     * 状态(01:保存、02:待审核、03:审核通过、04:审核未通过)
     */
    private String status;

}
