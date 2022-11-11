package com.duang.web;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Slf4j
@MapperScan({"com.duang.web.mapper","com.duang.support.support.mapper"})
@EnableDiscoveryClient
@EnableFeignClients //开启Fegin
@ComponentScan({"com.duang.support.support","com.duang.web","com.duang.cloud.api","com.duang.handler"})
public class WebApplication {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
        log.info("web启动成功");
    }

}
