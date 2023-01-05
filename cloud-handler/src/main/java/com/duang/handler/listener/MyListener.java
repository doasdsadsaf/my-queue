package com.duang.handler.listener;

import com.alibaba.fastjson.JSON;
import com.duang.cloudcommons.domain.TaskInfo;
import com.duang.handler.service.ConsumeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @创建人 dw
 * @创建时间 2021/12/28
 * @描述
 */
@Component
@Slf4j
public class MyListener {

    @Autowired
    private ConsumeService consumeService;

    /**
     * 监听某个队列的消息
     * @param message 接收到的消息
     */
    @RabbitListener(queues = "queue")
    public void myListener1(String message){
        log.info("消费者接收到的消息为：{}" , message);
    }

    /**
     * 监听austin发送过来的消息
     * @param message 接收到的消息
     */
    @RabbitListener(queues = "austin_queue")
    public void austinListener(String message){
        if (StringUtils.isBlank(message)) {
            return;
        }
        List<TaskInfo> taskInfoLists = JSON.parseArray(message, TaskInfo.class);
        consumeService.consume2Send(taskInfoLists);
    }
}
