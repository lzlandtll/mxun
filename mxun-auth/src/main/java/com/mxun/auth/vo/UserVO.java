package com.mxun.auth.vo;

import lombok.Data;

import java.util.Set;

/**
 * @Description: 认证成功之后返回的vo
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
    private Set<String> roleCodes;
}
