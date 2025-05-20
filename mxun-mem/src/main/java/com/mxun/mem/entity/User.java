package com.mxun.mem.entity;

import com.mxun.common.core.CommonEntity;
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
public class User extends CommonEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 10020247L;

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

}
