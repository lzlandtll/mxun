package com.mxun.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * @Description: 用来处理消息转换的
 * @Author: liuzhilin
 * @Date: 2025/3/1
 */
@Configuration
public class HttpMessageConvertersConfig {

    @Bean
    public org.springframework.boot.autoconfigure.http.HttpMessageConverters customConverters() {
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        return new org.springframework.boot.autoconfigure.http.HttpMessageConverters(jacksonConverter);
    }
}
