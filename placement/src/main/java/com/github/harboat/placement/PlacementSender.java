package com.github.harboat.placement;

import com.github.harboat.clients.core.placement.GamePlacement;
import com.github.harboat.rabbitmq.RabbitMQMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlacementSender {

    private final RabbitMQMessageProducer producer;

    @Value("${rabbitmq.exchanges.game}")
    private String internalExchange;

    @Value("${rabbitmq.routing-keys.game}")
    private String gameRoutingKey;

    void sendPlacement(GamePlacement gamePlacement) {
        producer.publish(gamePlacement, internalExchange, gameRoutingKey);
    }

}
