package com.mxun.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: web配置
 * @Author: liuzhilin
 * @Date: 2025/3/6
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加用户信息拦截器
        registry.addInterceptor(new UserInfoInterceptor()).addPathPatterns("/**");
    }
}
