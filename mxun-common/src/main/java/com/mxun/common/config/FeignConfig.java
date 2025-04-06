package com.mxun.common.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Retryer feignRetryer() {
        // 参数说明：
        // - period: 初始重试间隔时间（毫秒）
        // - maxPeriod: 最大重试间隔时间（毫秒）
        // - maxAttempts: 最大重试次数（包括第一次调用）
        return new Retryer.Default(1000, 3000, 3); // 初始间隔 1 秒，最大间隔 3 秒，最多重试 3 次
    }
}
