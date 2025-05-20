package com.mxun.common.utils;

import com.mxun.common.constant.RedisConstant;
import com.mxun.common.dto.UserInfoDTO;
import com.mxun.common.enums.RoleEnum;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Description: 用户工具类
 * @Author: liuzhilin
 * @Date: 2025/3/6
 */
@Slf4j
@Component
public class UserUtil {
    private static final ThreadLocal<Long> userIdHolder = new ThreadLocal<>();
    private static final ThreadLocal<String> userTokenHolder = new ThreadLocal<>();


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
    public static Set<String> getUserRoleList(){
        String userCacheKey = String.join(":", RedisConstant.USER_PREFIX, String.valueOf(userIdHolder.get()));
        RBucket<UserInfoDTO> bucket = redissonClient.getBucket(userCacheKey);
        UserInfoDTO userInfoDTO = bucket.get();
        return Optional.of(userInfoDTO.getRoleCodes()).orElse(new HashSet<>());
    }

    /**
     * @Description: 拦截器设置用户id
     * @Author: liuzhilin
     * @Date: 2025/3/8 17:19
     */
    public static void setUserId(HttpServletRequest request) {
        try {
            Enumeration<String> headers = request.getHeaders(HttpHeaders.AUTHORIZATION);
            if(Objects.equals(headers, null) || !headers.hasMoreElements()){
                return;
            }
            String authHeader = headers.nextElement();
            if(Objects.equals(authHeader, null)){
                return;
            }
            String token = authHeader.substring(7);
            Claims claims = JwtTokenUtil.validateToken(token);
            userIdHolder.set(Long.valueOf(claims.getSubject()));
            userTokenHolder.set(token);
        }catch (Exception e){
            System.out.println("解析用户token失败");
            log.error("e => ", e);
        }
    }

    public static Long getUserId() {
        return userIdHolder.get();
    }
    public static String getUserName() {
        String userCacheKey = String.join(":", RedisConstant.USER_PREFIX, String.valueOf(userIdHolder.get()));
        RBucket<UserInfoDTO> bucket = redissonClient.getBucket(userCacheKey);
        UserInfoDTO userInfoDTO = bucket.get();
        return userInfoDTO.getUsername();
    }
    public static String getUserToken() {
        return userTokenHolder.get();
    }

    public static Boolean hasLogin(){
        return userIdHolder.get() != null;
    }

    /**
     * @Description: 获取用户aiKey
     * @Author: liuzhilin
     * @Date: 2025/3/12 21:09
     */
    public static String getAiKey() {
        String userCacheKey = String.join(":", RedisConstant.USER_PREFIX, String.valueOf(userIdHolder.get()));
        RBucket<UserInfoDTO> bucket = redissonClient.getBucket(userCacheKey);
        UserInfoDTO userInfoDTO = bucket.get();
        return userInfoDTO.getAiKey();
    }

    /**
     * @Description: 删除用户aiKey
     * @Author: liuzhilin
     * @Date: 2025/3/13 9:38
     */
    public static void removeAiKey() {
        String userCacheKey = String.join(":", RedisConstant.USER_PREFIX, String.valueOf(userIdHolder.get()));
        RBucket<UserInfoDTO> bucket = redissonClient.getBucket(userCacheKey);
        UserInfoDTO userInfoDTO = bucket.get();
        userInfoDTO.setAiKey(null);
        Set<String> roleCodeSet = userInfoDTO.getRoleCodes();
        roleCodeSet.remove(RoleEnum.CHAT_AI.getRoleCode());
        bucket.set(userInfoDTO);
    }

    /**
     * @Description: 设置用户aiKey,并且添加AI角色权限
     * @Author: liuzhilin
     * @Date: 2025/3/12 21:09
     */
    public static void setAiKey(String aiKey) {
        String userCacheKey = String.join(":", RedisConstant.USER_PREFIX, String.valueOf(userIdHolder.get()));
        RBucket<UserInfoDTO> bucket = redissonClient.getBucket(userCacheKey);
        UserInfoDTO userInfoDTO = bucket.get();
        userInfoDTO.setAiKey(aiKey);
        Set<String> roleCodes = userInfoDTO.getRoleCodes();
        roleCodes.add(RoleEnum.CHAT_AI.getRoleCode());
        bucket.set(userInfoDTO);
    }


}
