package com.duang.handler.receiver.eventbus;

import com.duang.cloudcommons.domain.TaskInfo;
import com.duang.handler.service.ConsumeService;
import com.duang.support.support.constans.MessageQueuePipeline;
import com.duang.support.support.domain.MessageTemplate;
import com.duang.support.support.mq.eventbus.EventBusListener;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 3y
 */
@Component
@ConditionalOnProperty(name = "austin.mq.pipeline", havingValue = MessageQueuePipeline.EVENT_BUS)
public class EventBusReceiver implements EventBusListener {

    @Autowired
    private ConsumeService consumeService;

    @Override
    @Subscribe
    public void consume(List<TaskInfo> lists) {
        consumeService.consume2Send(lists);

    }

    @Override
    @Subscribe
    public void recall(MessageTemplate messageTemplate) {
        consumeService.consume2recall(messageTemplate);
    }
}
