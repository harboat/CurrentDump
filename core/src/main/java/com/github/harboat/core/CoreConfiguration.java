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

    @Value("${rabbitmq.exchanges.placement-response}")
    private String internalExchange;

    @Value("${rabbitmq.queues.placement-response}")
    private String placementResponseQueue;

    @Value("${rabbitmq.routing-keys.placement-response}")
    private String internalPlacementResponseRoutingKey;

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(internalExchange);
    }

    @Bean
    public Queue placementQueue() {
        return new Queue(placementResponseQueue);
    }

    @Bean
    public Binding internalToPlacementBinding() {
        return BindingBuilder
                .bind(placementQueue())
                .to(internalTopicExchange())
                .with(internalPlacementResponseRoutingKey);
    }
}
