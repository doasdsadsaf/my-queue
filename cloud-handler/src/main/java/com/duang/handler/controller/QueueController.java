package com.duang.handler.controller;

import com.duang.cloudcommons.entity.User;
import com.duang.handler.service.QueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("queue")
public class QueueController {

    @Resource
    private QueueService queueService;

    @RequestMapping("/get")
    @ResponseBody
    public User get(Long id){
        User user = queueService.get(id);
        return user;
    }

    @RequestMapping("/save")
    @ResponseBody
    User save(@RequestBody User user){
        queueService.save(user);
        return user;
    }

}
