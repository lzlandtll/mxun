package com.mxun.auth.entity;

import com.mxun.common.core.CommonEntity;
import com.mybatisflex.annotation.Table;

import java.io.Serializable;

import lombok.*;

/**
 * 用户基本信息表 实体类
 * @author liuzhilin
 * @since 2025-03-01
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table("sys_user")
@Data
public class User extends CommonEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 用户名
     */
    private String username;

    /**
     * 密码哈希值
     */
    private String password;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 用户电话
     */
    private String tel;

    /**
     * 用户类型(00:超级管理员,01:后台管理员,02:普通用户,03:三方用户)
     */
    private String userType;

    /**
     * 通义千问密钥
     */
    private String aiKey;

}
