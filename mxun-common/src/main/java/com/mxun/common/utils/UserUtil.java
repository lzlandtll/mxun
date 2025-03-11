package com.mxun.common.utils;

import com.mxun.common.constant.RedisConstant;
import com.mxun.common.dto.UserInfoDTO;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Enumeration;
import java.util.List;

/**
 * @Description: 用户工具类
 * @Author: liuzhilin
 * @Date: 2025/3/6
 */
@Component
public class UserUtil {
    private static final ThreadLocal<Long> userIdHolder = new ThreadLocal<>();


    private static RedissonClient redissonClient;

    public UserUtil(RedissonClient redissonClient){
        UserUtil.redissonClient = redissonClient;
    }

    /**
     * @Description: 设置用户缓存信息
     * @Author: liuzhilin
     * @Date: 2025/3/9 11:31
     */
    public static void setUserCache(UserInfoDTO userInfoDTO){
        String userCacheKey = String.join(":", RedisConstant.USER_PREFIX, String.valueOf(userInfoDTO.getUserId()));
        RBucket<UserInfoDTO> bucket = redissonClient.getBucket(userCacheKey);
        bucket.set(userInfoDTO);
    }

    /**
     * @Description: 获取用户角色信息
     * @Author: liuzhilin
     * @Date: 2025/3/9 11:31
     */
    public static List<Long> getUserRoleList(){
        String userCacheKey = String.join(":", RedisConstant.USER_PREFIX, String.valueOf(userIdHolder.get()));
        RBucket<UserInfoDTO> bucket = redissonClient.getBucket(userCacheKey);
        UserInfoDTO userInfoDTO = bucket.get();
        return userInfoDTO.getRoleList();
    }

    /**
     * @Description: 拦截器设置用户id
     * @Author: liuzhilin
     * @Date: 2025/3/8 17:19
     */
    public static void setUserId(HttpServletRequest request) {
        try {
            Enumeration<String> headers = request.getHeaders(HttpHeaders.AUTHORIZATION);
            String authHeader = headers.nextElement();
            String token = authHeader.substring(7);
            Claims claims = JwtTokenUtil.validateToken(token);
            userIdHolder.set(Long.valueOf(claims.getSubject()));
        }catch (Exception e){
            System.out.println("解析用户token失败");
        }
    }

    public static Long getUserId() {
        return userIdHolder.get();
    }


}
