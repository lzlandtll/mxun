package com.mxun.chatai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.mxun"})
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MxunChataiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MxunChataiApplication.class, args);
    }

}
