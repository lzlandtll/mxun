package com.mxun.sys.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mxun.sys.entity.RoleUser;
import com.mxun.sys.mapper.RoleUserMapper;
import com.mxun.sys.service.RoleUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 角色用户关联表 服务层实现。
 *
 * @author moxuan
 * @since 2025-03-08
 */
@Service
public class RoleUserServiceImpl extends ServiceImpl<RoleUserMapper, RoleUser> implements RoleUserService {

    @Override
    public List<Long> getRolesByUserId( Long userId) {
        QueryWrapper query = this.query();
        query.select(RoleUser::getRoleId);
        query.eq(RoleUser::getUserId, userId);
        List<RoleUser> list = list(query);
        if(!CollectionUtils.isEmpty(list)){
            return list.stream().map(RoleUser::getRoleId).toList();
        }
        return List.of();
    }
}
