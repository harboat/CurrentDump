package com.github.harboat.core;

import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class CoreConfiguration {

    @Value("${rabbitmq.exchanges.notification}")
    private String internalExchange;

    @Value("${rabbitmq.queues.notification}")
    private String notificationQueue;

    @Value("${rabbitmq.routing-keys.notification}")
    private String internalNotificationRoutingKey;

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(internalExchange);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(notificationQueue);
    }

    @Bean
    public Binding internalToNotificationBinding() {
        return BindingBuilder
                .bind(notificationQueue())
                .to(internalTopicExchange())
                .with(internalNotificationRoutingKey);
    }
}
