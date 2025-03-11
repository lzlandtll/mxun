package com.mxun.sys.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mxun.sys.entity.RoleApi;
import com.mxun.sys.mapper.RoleApiMapper;
import com.mxun.sys.service.RoleApiService;
import org.springframework.stereotype.Service;

/**
 * 角色接口关联表 服务层实现。
 *
 * @author moxuan
 * @since 2025-03-08
 */
@Service
public class RoleApiServiceImpl extends ServiceImpl<RoleApiMapper, RoleApi> implements RoleApiService {

}
