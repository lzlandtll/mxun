package com.mxun.common.dto;

import lombok.Data;

/**
 * @Description: 方法系统验证用户信息
 * @Author: liuzhilin
 * @Date: 2025/3/1
 */
@Data
public class ThirdUserInfo {
    // 用户名
    private String username;

    // 用户密码,安全起见一般需要用时间戳进行加密
    private String password;
}
