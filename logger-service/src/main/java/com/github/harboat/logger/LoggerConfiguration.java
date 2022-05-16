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

    @Value("${rabbitmq.queues.core-logger}")
    private String coreQueue;

    @Value("${rabbitmq.routing-keys.internal-core-logger}")
    private String internalCoreLoggerRoutingKey;

    @Value("${rabbitmq.queues.game-logger}")
    private String gameQueue;

    @Value("${rabbitmq.routing-keys.internal-game-logger}")
    private String internalGameLoggerRoutingKey;

    @Value("${rabbitmq.queues.placement-logger}")
    private String placementQueue;

    @Value("${rabbitmq.routing-keys.internal-placement-logger}")
    private String internalPlacementLoggerRoutingKey;

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(internalExchange);
    }

    @Bean
    public Queue coreQueue() {
        return new Queue(coreQueue);
    }

    @Bean
    public Binding internalToCoreBinding() {
        return BindingBuilder
                .bind(coreQueue())
                .to(internalTopicExchange())
                .with(internalCoreLoggerRoutingKey);
    }


    @Bean
    public Queue gameQueue() {
        return new Queue(gameQueue);
    }

    @Bean
    public Binding internalToGameBinding() {
        return BindingBuilder
                .bind(gameQueue())
                .to(internalTopicExchange())
                .with(internalGameLoggerRoutingKey);
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
