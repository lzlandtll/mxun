package com.mxun.sys.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @Description: 用于接收数据
 * @Author: liuzhilin
 * @Date: 2025/3/4
 */
@Data
public class UserDTO {
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "00003")
    private String tel;
    @NotBlank(message = "01007")
    private String password;
    @NotBlank(message = "02005")
    private String code;
}
