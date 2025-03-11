package com.mxun.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: redis用户缓存信息
 * @Author: liuzhilin
 * @Date: 2025/3/8
 */
@Data
public class UserInfoDTO implements Serializable {
    // 用户id
    private Long userId;

    // 用户名称
    private String username;

    // 用户类型
    private String userType;

    // 用户邮箱
    private String email;

    // 用户电话
    private String tel;

    // 用户角色
    private List<Long> roleList;
}
