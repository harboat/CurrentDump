package com.github.harboat.placement;

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
public class PlacementConfiguration {

    @Value("${rabbitmq.exchanges.placement}")
    private String internalExchange;

    @Value("${rabbitmq.queues.placement}")
    private String placementQueue;

    @Value("${rabbitmq.routing-keys.placement}")
    private String internalPlacementRoutingKey;

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(internalExchange);
    }

    @Bean
    public Queue placementQueue() {
        return new Queue(placementQueue);
    }

    @Bean
    public Binding internalToPlacementBinding() {
        return BindingBuilder
                .bind(placementQueue())
                .to(internalTopicExchange())
                .with(internalPlacementRoutingKey);
    }
}
