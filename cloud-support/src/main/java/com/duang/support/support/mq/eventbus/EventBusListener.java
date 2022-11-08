package com.duang.support.support.mq.eventbus;


import com.duang.cloudcommons.domain.TaskInfo;
import com.duang.support.support.domain.MessageTemplate;

import java.util.List;

/**
 * @author 3y
 * 监听器
 */
public interface EventBusListener {


    /**
     * 消费消息
     * @param lists
     */
    void consume(List<TaskInfo> lists);

    /**
     * 撤回消息
     * @param messageTemplate
     */
    void recall(MessageTemplate messageTemplate);
}
