package com.mxun.sys.service;

import com.mybatisflex.core.service.IService;
import com.mxun.sys.entity.RoleUser;

import java.util.List;
import java.util.Set;

/**
 * 角色用户关联表 服务层。
 *
 * @author moxuan
 * @since 2025-03-08
 */
public interface RoleUserService extends IService<RoleUser> {

    Set<String> getRoleCodesByUserId(Long userId);

    void addRoleUser(Long userId, String roleCode);

    void removeRoleUser(Long userId, String roleCode);
}
