package com.mxun.mem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.mxun.*.mapper")
@ComponentScan(basePackages = {"com.mxun"})
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MxunMemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MxunMemApplication.class, args);
    }

}
