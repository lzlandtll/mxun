package com.mxun.mem.entity;

import com.mxun.common.core.CommonEntity;
import com.mybatisflex.annotation.Id;
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
 * 用户关注关系表 实体类。
 *
 * @author moxuan
 * @since 2025-04-07
 */
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
@Table("mem_user_follow")
public class UserFollow extends CommonEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1256L;


    /**
     * 关注者用户 ID
     */
    private Long followerUserId;

    /**
     * 被关注者用户 ID
     */
    private Long followedUserId;


}
