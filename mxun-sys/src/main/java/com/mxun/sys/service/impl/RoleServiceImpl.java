package com.mxun.sys.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mxun.sys.entity.Role;
import com.mxun.sys.mapper.RoleMapper;
import com.mxun.sys.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * 角色表 服务层实现。
 *
 * @author moxuan
 * @since 2025-03-08
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
