package com.mxun.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/6
 */
@Configuration
public class AppExecutorConfig {
    @Value(value = "${custom.thread.corePoolSize:5}")
    private int corePoolSize;

    @Value(value = "${custom.thread.maxPoolSize:20}")
    private int maxPoolSize;

    @Value(value = "${custom.thread.queueCapacity:100}")
    private int queueCapacity;

    @Bean
    public ThreadPoolTaskExecutor appExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize); // 核心线程数
        executor.setMaxPoolSize(maxPoolSize); // 最大线程数
        executor.setQueueCapacity(queueCapacity); // 队列容量
        executor.initialize();
        return executor;
    }
}
