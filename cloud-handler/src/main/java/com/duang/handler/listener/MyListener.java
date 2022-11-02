package com.duang.handler.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @创建人 dw
 * @创建时间 2021/12/28
 * @描述
 */
@Component
@Slf4j
public class MyListener {
    /**
     * 监听某个队列的消息
     * @param message 接收到的消息
     */
    @RabbitListener(queues = "queue")
    public void myListener1(String message){
        log.info("消费者接收到的消息为：{}" , message);
    }
}
