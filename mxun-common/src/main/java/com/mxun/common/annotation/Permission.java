package com.mxun.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 权限注解
 * @Author: liuzhilin
 * @Date: 2025/3/8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Permission {
    // 接口编码: ${模块名}.${接口层}.${方法名}
    String apiCode();

    // 错误编码: 使用ErrorEnum里面具体项的错误编码
    String errorCode();
}
