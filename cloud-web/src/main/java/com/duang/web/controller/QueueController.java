package com.duang.web.controller;

import com.duang.cloudcommons.entity.MessageResponse;
import com.duang.cloudcommons.entity.Order;
import com.duang.cloudcommons.entity.User;
import com.duang.web.config.RabbitMQConfig;
import com.duang.web.feign.HandlerFeign;
import com.duang.web.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/web")
@Slf4j
@RefreshScope // 读取动态配置文件
public class QueueController {

    @Resource
    private HandlerFeign handlerFeign;

    //注入RabbitMQ的模板
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderService orderService;

    @Value("${server.datasource.url}")
    private String name;

    @Value("${config_cloud}")
    private String cloud;


    @Value("${config-web}")
    private String configWeb;

    @Autowired
    private ConfigurableApplicationContext applicationContext;

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
    public String  sendQueue(@RequestParam("news") String news){

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

    @GetMapping("getConfig")
    public String getConfig(){
        log.info("configWeb:{}",configWeb);
        log.info("cloud:{}",cloud);

        return configWeb ;
    }

    /**
     * 验证分布式事务
     */
    public MessageResponse<User> save(User user){
        Order order = new Order();
        orderService.save(order);
        handlerFeign.save(user);
        return MessageResponse.success();
    }



}
