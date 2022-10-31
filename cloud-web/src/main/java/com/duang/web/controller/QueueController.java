package com.duang.web.controller;

import com.duang.cloudcommons.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/web")
@Slf4j
public class QueueController {

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/get")
    @ResponseBody
    public User get(Long id){
        String url = "cloud-handler";

      //  log.info("=====从nacos中获取到的微服务地址:{}",url);
        User forObject = restTemplate.getForObject("http://" + url+"/queue/get" + "?id=" + id, User.class);
        log.info("查询到的数据:{}",forObject);
        return forObject;
    }
}
