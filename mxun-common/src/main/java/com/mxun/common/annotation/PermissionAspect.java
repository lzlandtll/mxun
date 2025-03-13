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
import java.util.Set;

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
    @Around(value = "@within(permission) || @annotation(permission)")
    public Object around(ProceedingJoinPoint joinPoint, Permission permission) throws Throwable {
        // 如果没有获取到类上面的注解，则获取方法上面的注解
        if(permission == null){
            Class<?> targetClass = joinPoint.getTarget().getClass();
            String methodName = joinPoint.getSignature().getName();
            permission = targetClass.getAnnotation(Permission.class);
        }

        String apiCode = permission.apiCode();
        String errorCode = permission.errorCode();

        // 获取拥有该接口权限的角色
        String key = String.join(":", RedisConstant.PERMISSION_INTERFACES_PREFIX, apiCode);
        RSet<Long> apiRoleSet = redissonClient.getSet(key);

        // 这里不能直接使用apiRoleSet,可能会清空这里面的数据
        HashSet<Long> apiRoleSetCopy = new HashSet<>(apiRoleSet);
        // 获取用户角色
        Set<Long> userRoleList = UserUtil.getUserRoleList();

        // 用户角色与拥有接口权限的角色有交集则标识有权限
        apiRoleSetCopy.retainAll(userRoleList);
        if(!apiRoleSetCopy.isEmpty()){
            return joinPoint.proceed();
        }

        // 否则抛出异常
        throw new BusinessException(ErrorEnum.getError(errorCode));
    }

    private Class<?>[] getParameterTypes(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Class<?>[] parameterTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }
        return parameterTypes;
    }
}
