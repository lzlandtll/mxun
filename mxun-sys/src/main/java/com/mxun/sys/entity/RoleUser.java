package com.mxun.sys.entity;

import com.mxun.common.core.CommonEntity;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 角色用户关联表 实体类。
 *
 * @author moxuan
 * @since 2025-03-08
 */
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
@Table("sys_role_user")
public class RoleUser extends CommonEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 用户id
     */
    private Long userId;
}
