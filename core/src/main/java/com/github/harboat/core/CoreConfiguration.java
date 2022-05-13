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
    private String internalNotificationExchange;

    @Value("${rabbitmq.queues.notification}")
    private String notificationQueue;

    @Value("${rabbitmq.routing-keys.notification}")
    private String internalNotificationRoutingKey;

    @Value("${rabbitmq.exchanges.core}")
    private String internalCoreExchange;

    @Value("${rabbitmq.queues.game-creation-response}")
    private String gameCreationResponseQueue;

    @Value("${rabbitmq.routing-keys.core-game-creation-response}")
    private String internalCoreGameCreationResponseRoutingKey;

    @Bean
    public TopicExchange internalNotificationExchange() {
        return new TopicExchange(internalNotificationExchange);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(notificationQueue);
    }

    @Bean
    public Binding internalNotificationToNotificationBinding() {
        return BindingBuilder
                .bind(notificationQueue())
                .to(internalNotificationExchange())
                .with(internalNotificationRoutingKey);
    }

    @Bean
    public TopicExchange internalCoreExchange() {
        return new TopicExchange(internalCoreExchange);
    }

    @Bean
    public Queue gameCreationResponseQueue() {
        return new Queue(gameCreationResponseQueue);
    }

    @Bean
    public Binding internalCoreToGameCreationResponseBinding() {
        return BindingBuilder
                .bind(gameCreationResponseQueue())
                .to(internalCoreExchange())
                .with(internalCoreGameCreationResponseRoutingKey);
    }

}
