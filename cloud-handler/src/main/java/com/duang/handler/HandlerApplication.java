package com.duang.handler;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan({"com.duang.handler.mapper"})
@EnableDiscoveryClient
@EnableFeignClients //开启Fegin
public class HandlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HandlerApplication.class, args);
    }

}
