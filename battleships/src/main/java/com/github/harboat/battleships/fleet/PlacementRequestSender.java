package com.github.harboat.battleships.fleet;

import com.github.harboat.clients.placement.PlacementRequest;
import com.github.harboat.rabbitmq.RabbitMQMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlacementRequestSender {

    private final RabbitMQMessageProducer producer;

    @Value("${rabbitmq.exchanges.placement}")
    private String internalExchange;

    @Value("${rabbitmq.routing-keys.internal-placement}")
    private String placementResponseRoutingKey;

    public void sendRequest(PlacementRequest placementRequest) {
        producer.publish(placementRequest, internalExchange, placementResponseRoutingKey);
    }

}
