package com.mxun.common.annotation;

import com.mxun.common.constant.RedisConstant;
import com.mxun.common.enums.ErrorEnum;
import com.mxun.common.resultView.BusinessException;
import com.mxun.common.utils.UserUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

/**
 * @Description: 接口权限校验逻辑
 * @Author: liuzhilin
 * @Date: 2025/3/8
 */
@Component
@Aspect
public class PermissionAspect {

    @Autowired
    private RedissonClient redissonClient;
    @Around(value = "@annotation(permission)")
    public Object around(ProceedingJoinPoint joinPoint, Permission permission) throws Throwable {
        String apiCode = permission.apiCode();
        String errorCode = permission.errorCode();

        // 获取拥有该接口权限的角色
        String key = String.join(":", RedisConstant.PERMISSION_INTERFACES_PREFIX, apiCode);
        RSet<Long> apiRoleSet = redissonClient.getSet(key);

        // 这里不能直接使用apiRoleSet,可能会清空这里面的数据
        HashSet<Long> apiRoleSetCopy = new HashSet<>(apiRoleSet);
        // 获取用户角色
        List<Long> userRoleList = UserUtil.getUserRoleList();

        // 用户角色与拥有接口权限的角色有交集则标识有权限
        if(apiRoleSetCopy.retainAll(userRoleList)){
            return joinPoint.proceed();
        }

        // 否则抛出异常
        throw new BusinessException(ErrorEnum.getError(errorCode));
    }
}
