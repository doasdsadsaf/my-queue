package com.duang.web.controller;

import com.duang.cloudcommons.entity.User;
import com.duang.web.RabbitMQConfig;
import com.duang.web.feign.HandlerFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/web")
@Slf4j
public class QueueController {

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private HandlerFeign handlerFeign;

    //注入RabbitMQ的模板
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/get")
    @ResponseBody
    public User get(Long id){
        String url = "cloud-handler";

      //  log.info("=====从nacos中获取到的微服务地址:{}",url);
      //  User forObject = restTemplate.getForObject("http://" + url+"/queue/get" + "?id=" + id, User.class);
        User user = handlerFeign.get(id);
        log.info("查询到的数据:{}",user);
        return user;
    }



    @GetMapping("sendQueue")
    public String  sendQueue(@RequestParam("news")String news){

        /**
         * 发送消息
         * 参数一：交换机名称
         * 参数二：路由key
         * 参数三：发送的消息
         */
        rabbitTemplate.convertAndSend(RabbitMQConfig.ITEM_TOPIC_EXCHANGE ,"item.queue" ,news);
        // 返回消息
        return "发送消息成功！";
    }
}
