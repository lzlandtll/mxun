package com.mxun.mem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/5
 */
@Data
public class SyncUserDTO {
    // 用户编码,sys和mem都是用的id作为用户编码
    @NotNull(message = "01013")
    private Long id;
}
