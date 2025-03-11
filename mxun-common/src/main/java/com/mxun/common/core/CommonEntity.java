package com.mxun.common.core;

import com.mybatisflex.annotation.Column;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description: 通用实体类
 * @Author: liuzhilin
 * @Date: 2025/3/1
 */
@Data
public class CommonEntity extends IdEntity {

    @Column("create_by")
    private Long createBy;

    @Column("create_time")
    private LocalDateTime createTime;

    @Column("update_by")
    private Long updateBy;

    @Column("update_time")
    private LocalDateTime updateTime;

    // 逻辑删除字典
    @Column(value = "is_deleted", isLogicDelete = true)
    private Boolean isDeleted;
}
