package com.github.harboat.logger;

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
public class LoggerConfiguration {

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queues.main-logger}")
    private String mainQueue;

    @Value("${rabbitmq.queues.placement-logger}")
    private String placementQueue;

    @Value("${rabbitmq.routing-keys.internal-placement-logger}")
    private String internalPlacementLoggerRoutingKey;

    @Value("${rabbitmq.routing-keys.internal-main-logger}")
    private String internalMainLoggerRoutingKey;

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(internalExchange);
    }

    @Bean
    public Queue mainQueue() {
        return new Queue(mainQueue);
    }

    @Bean
    public Binding internalToMainBinding() {
        return BindingBuilder
                .bind(mainQueue())
                .to(internalTopicExchange())
                .with(internalMainLoggerRoutingKey);
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
                .with(internalPlacementLoggerRoutingKey);
    }

}
