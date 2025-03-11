package com.mxun.gateway.vo;

import lombok.Data;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/3/1
 */
@Data
public class UserVO {
    private Long id;
    private String username;
    private String email;
    private String tel;
    private String token;
}
