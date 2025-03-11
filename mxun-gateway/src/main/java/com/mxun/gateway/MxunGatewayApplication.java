package com.mxun.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


//@ComponentScan(basePackages = {"com.mxun.gateway"})
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MxunGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MxunGatewayApplication.class, args);
    }

}
