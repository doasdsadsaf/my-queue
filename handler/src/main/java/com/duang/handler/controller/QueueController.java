package com.duang.handler.controller;

import com.duang.cloudcommons.entity.User;
import com.duang.handler.service.QueueService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
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

}
