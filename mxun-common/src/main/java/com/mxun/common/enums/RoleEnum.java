package com.mxun.common.enums;

/**
 * @Description: 短信模板枚举
 * @Author: liuzhilin
 * @Date: 2025/3/4
 */
public enum RoleEnum {
    CHAT_AI("CHAT_AI", "AI功能角色");

    RoleEnum(String roleCode, String roleName){
        this.roleCode = roleCode;
        this.roleName = roleName;
    }
    private String roleCode;
    private String roleName;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
