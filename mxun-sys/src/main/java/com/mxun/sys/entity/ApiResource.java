package com.mxun.sys.entity;

import com.mxun.common.core.CommonEntity;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;

import java.io.Serial;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * api资源表 实体类。
 *
 * @author moxuan
 * @since 2025-03-08
 */
@Accessors(chain = true)
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
@Table("sys_api_resource")
public class ApiResource extends CommonEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 接口code,注解上根据该编码进行限制
     */
    private String apiCode;

    /**
     * 接口描述
     */
    private String apiDescription;
}
