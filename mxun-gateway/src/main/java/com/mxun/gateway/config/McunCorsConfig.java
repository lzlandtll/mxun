package com.mxun.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

/**
 * @Description: 设置跨域请求
 * @Author: liuzhilin
 * @Date: 2025/3/9 10:35
 */
@Configuration
public class McunCorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 设置允许特定的源而不是使用通配符
        corsConfiguration.addAllowedOrigin("http://localhost:5173");
        corsConfiguration.addAllowedOrigin("http://localhost:5174");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(source);
    }
}