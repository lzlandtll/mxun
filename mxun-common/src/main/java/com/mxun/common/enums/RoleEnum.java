package com.mxun.common.enums;

/**
 * @Description: 短信模板枚举
 * @Author: liuzhilin
 * @Date: 2025/3/4
 */
public enum RoleEnum {
    CHAT_AI(1L, "AI功能角色");

    RoleEnum(Long roleId, String roleName){
        this.roleId = roleId;
        this.roleName = roleName;
    }
    private Long roleId;
    private String roleName;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
