package com.mxun.mem.entity;

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
 *  实体类。
 *
 * @author moxuan
 * @since 2025-04-04
 */
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
@Table("mem_user")
public class User extends Model<User> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 编写文章总数
     */
    private Long articleCount;

    /**
     * 文章总点赞数
     */
    private Long articleLikeCount;

    /**
     * 文章总的收藏数
     */
    private Long articleCollectionCount;

    /**
     * 粉丝数量
     */
    private Long followCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人ID
     */
    private String createBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 修改用户ID
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
