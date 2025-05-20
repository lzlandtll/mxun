package com.mxun.sys.vo;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/8
 */
@Data
public class PersonalUserVO {
    private Long userId;

    @NotNull(message = "01015")
    private String username;

    private String summary;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "00003")
    private String tel;

    @Nullable
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "00008")
    private String email;
}
