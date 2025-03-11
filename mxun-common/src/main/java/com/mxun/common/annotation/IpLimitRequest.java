package com.mxun.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: ip访问限制注解,针对单个接口进行限制
 * @Author: liuzhilin
 * @Date: 2025/3/5
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IpLimitRequest {
    // 接口路径: /${模块名}/${接口层}/${方法名}
    String path();

    // 限制次数
    int limit();

    // 限制时间
    int expireSecond();
}
