package com.mxun.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan("com.mxun")
@SpringBootApplication
public class MxunThirdPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MxunThirdPartyApplication.class, args);
    }

}
