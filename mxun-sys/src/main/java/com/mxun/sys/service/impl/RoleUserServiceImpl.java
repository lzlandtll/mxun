package com.mxun.sys.service.impl;

import com.mxun.common.enums.ErrorEnum;
import com.mxun.common.resultView.BusinessException;
import com.mxun.common.utils.UserUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mxun.sys.entity.RoleUser;
import com.mxun.sys.mapper.RoleUserMapper;
import com.mxun.sys.service.RoleUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色用户关联表 服务层实现。
 *
 * @author moxuan
 * @since 2025-03-08
 */
@Service
public class RoleUserServiceImpl extends ServiceImpl<RoleUserMapper, RoleUser> implements RoleUserService {

    @Override
    public Set<Long> getRolesByUserId(Long userId) {
        QueryWrapper query = this.query();
        query.select(RoleUser::getRoleId);
        query.eq(RoleUser::getUserId, userId);
        List<RoleUser> list = list(query);
        if(!CollectionUtils.isEmpty(list)){
            return list.stream().map(RoleUser::getRoleId).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    @Override
    public void addRoleUser(Long userId, Long roleId) {
        QueryWrapper query = this.query();
        query.eq(RoleUser::getUserId, userId);
        query.eq(RoleUser::getRoleId, roleId);
        RoleUser roleUserDb = this.getOne(query);
        if(Objects.nonNull(roleUserDb)){
            throw new BusinessException(ErrorEnum.SYS_ROLE_REPEAT_ADD_ERROR);
        }
        RoleUser roleUser = new RoleUser();
        roleUser.setUserId(userId);
        roleUser.setRoleId(roleId);
        this.save(roleUser);
    }

    @Override
    public void removeRoleUser(Long userId, Long roleId) {
        QueryWrapper query = this.query();
        query.eq(RoleUser::getUserId, userId);
        query.eq(RoleUser::getRoleId, roleId);
        this.remove(query);

        UserUtil.removeAiKey();
    }
}
