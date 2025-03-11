package com.mxun.chatai.enums;

/**
 * @Description: 通义千问发送消息的角色类型
 * @Author: liuzhilin
 * @Date: 2025/1/19
 */
public enum ChatRoleEnum {
    USER("user", "用户"),
    ASSISTANT("assistant", "助手"),
    SYSTEM("system", "系统");

    ChatRoleEnum(String role, String name) {
        this.role = role;
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    private String role;
    private String name;
}
