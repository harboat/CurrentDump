package com.github.harboat.placement;

import com.github.harboat.clients.placement.PlacementResponse;
import com.github.harboat.rabbitmq.RabbitMQMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlacementSender {

    private final RabbitMQMessageProducer producer;

    @Value("${rabbitmq.exchanges.response}")
    private String internalExchange;

    @Value("${rabbitmq.routing-keys.placement-response}")
    private String placementResponseRoutingKey;

    void sendResponse(PlacementResponse placementResponse) {
        producer.publish(placementResponse, internalExchange, placementResponseRoutingKey);
    }

}
