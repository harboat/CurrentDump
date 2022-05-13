package com.github.harboat.core.games;

import com.github.harboat.clients.battleships.GameCreationRequest;
import com.github.harboat.rabbitmq.RabbitMQMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameCreationQueueProducer {

    private final RabbitMQMessageProducer producer;

    @Value("${rabbitmq.exchanges.game}")
    private String internalExchange;

    @Value("${rabbitmq.routing-keys.game-creation}")
    private String gameCreationRoutingKey;

    public void sendRequest(GameCreationRequest request) {
        producer.publish(request, internalExchange, gameCreationRoutingKey);
    }


}
