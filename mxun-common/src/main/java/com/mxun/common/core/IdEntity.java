package com.mxun.common.core;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import lombok.Data;

/**
 * @Description: 主键实体类
 * @Author: liuzhilin
 * @Date: 2025/3/1
 */
@Data
public class IdEntity {
    @Id(keyType = KeyType.Auto, value = "id")
    private Long id;

    @Column("version")
    private Long version;
}
