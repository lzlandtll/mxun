package com.mxun.sys.service.impl;

import com.mxun.common.constant.RedisConstant;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mxun.sys.entity.RoleApi;
import com.mxun.sys.mapper.RoleApiMapper;
import com.mxun.sys.service.RoleApiService;
import jakarta.annotation.PostConstruct;
import org.redisson.api.RBucket;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色接口关联表 服务层实现。
 *
 * @author moxuan
 * @since 2025-03-08
 */
@Service
public class RoleApiServiceImpl extends ServiceImpl<RoleApiMapper, RoleApi> implements RoleApiService {

    @Autowired
    RedissonClient redissonClient;

    @PostConstruct
    public void initRoleApi() {
        RBucket<Object> bucket = redissonClient.getBucket(RedisConstant.PERMISSION_INTERFACES_PREFIX);
        if(Objects.nonNull(bucket) && Objects.nonNull(bucket.get())){
            return;
        }
        List<RoleApi> roleApiList = super.list();
        Map<String, List<RoleApi>> apiCodeRoles = roleApiList.stream().collect(Collectors.groupingBy(RoleApi::getApiCode));
        apiCodeRoles.forEach((apiCode, roleApis) -> {
            String key = String.join(":", RedisConstant.PERMISSION_INTERFACES_PREFIX, apiCode);
            RSet<String> apiRoleCodeSet = redissonClient.getSet(key);
            apiRoleCodeSet.addAll(roleApis.stream().map(RoleApi::getRoleCode).collect(Collectors.toSet()));
        });
    }
}
