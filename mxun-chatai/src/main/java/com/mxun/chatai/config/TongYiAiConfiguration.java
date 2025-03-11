package com.mxun.chatai.config;

import com.alibaba.dashscope.aigc.generation.Generation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 通义千问配置文件
 * @Author: liuzhilin
 * @Date: 2025/1/14
 */
@Configuration
public class TongYiAiConfiguration {

    @Bean
    public Generation generation(){
        return new Generation();
    }
}
