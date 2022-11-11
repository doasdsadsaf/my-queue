package com.duang.handler.handler;


import com.duang.cloudcommons.domain.TaskInfo;
import com.duang.support.support.domain.MessageTemplate;

/**
 * @author 3y
 * 消息处理器
 */
public interface Handler {

    /**
     * 处理器
     *
     * @param taskInfo
     */
    void doHandler(TaskInfo taskInfo);

    /**
     * 撤回消息
     *
     * @param messageTemplate
     * @return
     */
    void recall(MessageTemplate messageTemplate);


}
