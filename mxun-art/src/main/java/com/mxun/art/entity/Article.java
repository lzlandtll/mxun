package com.mxun.art.entity;

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
public class Article extends Model<Article> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

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
    private Integer categoryId;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 收藏数
     */
    private Integer collectionCount;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 状态(01:保存、02:待审核、03:审核通过、04:审核未通过)
     */
    private String status;

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
