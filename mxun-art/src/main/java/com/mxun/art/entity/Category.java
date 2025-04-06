package com.mxun.art.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import com.mybatisflex.core.activerecord.Model;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户创建的分类表 实体类。
 *
 * @author moxuan
 * @since 2025-03-31
 */
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
@Table("art_category")
public class Category extends Model<Category> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 集合ID
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 用户ID, 关联mxun-sys.sys_user.id
     */
    private Long userId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类描述
     */
    private String description;

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
