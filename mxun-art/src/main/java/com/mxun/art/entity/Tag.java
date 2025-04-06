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
 * 关于文章的标签表 实体类。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@Accessors(chain = true)
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
@Table("art_tag")
public class Tag extends Model<Tag> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标签ID
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 标签名称
     */
    private String name;

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
