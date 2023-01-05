package com.duang.web.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @创建人 dw
 * @创建时间 2021/12/27
 * @描述
 */
@Configuration
public class RabbitMQConfig {

    //交换机名称
    @Value("${austin.rabbitmq.topic.name}")
    public static final String ITEM_TOPIC_EXCHANGE = "topic_exchange";

    public static final String ITEM_QUEUE = "queue";

    //交换机名称
    @Value("${austin.rabbitmq.exchange.name}")
    public String AUSTIN_TOPIC_EXCHANGE;

    @Value("${austin.rabbitmq.topic.name}")
    public String AUSTIN_QUEUE;

    //声明交换机
    @Bean("itemTopicExchange")
    public Exchange topicExchange(){
        // 创建一个交换机,并持久化
        return ExchangeBuilder.topicExchange(ITEM_TOPIC_EXCHANGE).durable(true).build();
    }

    // 声明队列
    @Bean("itemQueue")
    public Queue itemQueue() {
        return QueueBuilder.durable(ITEM_QUEUE).build();
    }

    /**
     * 再创建一个针对austin项目的交换机
     * @return
     */
    @Bean("austinTopicExchange")
    public Exchange austinTopicExchange(){
        // 创建一个交换机,并持久化
        return ExchangeBuilder.topicExchange(AUSTIN_TOPIC_EXCHANGE).durable(true).build();
    }

    /**
     * 再创建一个针对austin项目的消息队列
     * @return
     */
    @Bean("austinQueue")
    public Queue austinQueue(){
        // 创建一个交换机,并持久化
        return QueueBuilder.durable(AUSTIN_QUEUE).build();
    }


    // 绑定队列和交换机
    @Bean
    public Binding itemQueueExchange(@Qualifier("itemQueue") Queue queue,
                                     @Qualifier("itemTopicExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("item.#").noargs();
    }


    // 绑定Austin项目交换机和队列
    @Bean
    public Binding austinQueueExchange(@Qualifier("austinQueue") Queue queue,
                                     @Qualifier("austinTopicExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("austin_queue").noargs();
    }


}
