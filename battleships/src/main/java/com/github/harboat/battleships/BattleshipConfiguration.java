package com.github.harboat.battleships;

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
public class BattleshipConfiguration {

    @Value("${rabbitmq.exchanges.game}")
    private String internalGameExchange;

    @Value("${rabbitmq.queues.game-placement}")
    private String gamePlacementQueue;

    @Value("${rabbitmq.routing-keys.game-placement}")
    private String internalGamePlacementResponseRoutingKey;

    @Value("${rabbitmq.queues.game-creation}")
    private String gameCreationQueue;

    @Value("${rabbitmq.routing-keys.game-creation}")
    private String internalGameCreationRoutingKey;

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(internalGameExchange);
    }

    @Bean
    public Queue placementQueue() {
        return new Queue(gamePlacementQueue);
    }

    @Bean
    public Binding internalToPlacementBinding() {
        return BindingBuilder
                .bind(placementQueue())
                .to(internalTopicExchange())
                .with(internalGamePlacementResponseRoutingKey);
    }

    @Bean
    public Queue gameCreationQueue() {
        return new Queue(gameCreationQueue);
    }

    @Bean
    public Binding internalToGameCreationBinding() {
        return BindingBuilder
                .bind(gameCreationQueue())
                .to(internalTopicExchange())
                .with(internalGameCreationRoutingKey);
    }

}
