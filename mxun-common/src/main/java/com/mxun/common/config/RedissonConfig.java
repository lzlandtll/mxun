package com.mxun.common.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: redisson配置
 * @Author: liuzhilin
 * @Date: 2025/3/4
 */
@Configuration
public class RedissonConfig {

    @Value("${redisson.host}")
    private String redisHost;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(){
        // 1.创建配置
        Config config = new Config();
        config.useSingleServer().setAddress(redisHost);

        // 2.根据config创建出redissonClient示例
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
