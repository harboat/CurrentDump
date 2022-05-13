package com.github.harboat.battleships.game;

import com.github.harboat.clients.core.GameCreationResponse;
import com.github.harboat.rabbitmq.RabbitMQMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameCreationResponseProducer {

    private final RabbitMQMessageProducer producer;

    @Value("${rabbitmq.exchanges.core}")
    private String internalExchange;

    @Value("${rabbitmq.routing-keys.core-game-creation-response}")
    private String notificationRoutingKey;

    public void sendResponse(GameCreationResponse response) {
        producer.publish(response, internalExchange, notificationRoutingKey);
    }


}
