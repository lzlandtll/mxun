package com.mxun.sys.entity;

import com.mxun.common.core.CommonEntity;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;

import java.io.Serial;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色接口关联表 实体类。
 *
 * @author moxuan
 * @since 2025-03-08
 */
@Accessors(chain = true)
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
@Table("sys_role_api")
public class RoleApi extends CommonEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 接口code
     */
    private String apiCode;
}
