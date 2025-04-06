package com.mxun.common.constant;

/**
 * @Description: redis常量信息,主要用于区分各个缓存的前缀
 * @Author: liuzhilin
 * @Date: 2025/3/9
 */
public class RedisConstant {
    // 短信验证码前缀
    public static final String SMS_PREFIX = "SMS";

    // ip限制前缀
    public static final String IP_LIMIT_PREFIX = "IP_LIMIT";

    // 接口权限前缀
    public static final String PERMISSION_INTERFACES_PREFIX = "INTERFACES";

    // 用户缓存前缀
    public static final String USER_PREFIX = "USER_CACHE";

    // 存储失败kafka消息的游标前缀
    public static final String FAIL_KAFKA_CURSOR = "FAIL_KAFKA_CURSOR";
}
