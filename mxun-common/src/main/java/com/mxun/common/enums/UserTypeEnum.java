package com.mxun.common.enums;

/**
 * @Description: 用户类型枚举
 * @Author: liuzhilin
 * @Date: 2025/3/1
 */
public enum UserTypeEnum {
    SUPER_ADMIN("00", "超级管理员"),
    ADMIN("01", "普通管理员"),
    COMMON_USER("02", "普通用户"),
    THIRD_USER("03", "三方用户");

    private String userType;
    private String info;
    UserTypeEnum(String userType, String info) {
        this.userType = userType;
        this.info = info;
    }
    public String getUserType() {
        return userType;
    }
    public String getInfo() {
        return info;
    }
}
