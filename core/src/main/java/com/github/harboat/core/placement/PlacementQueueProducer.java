package com.github.harboat.core.placement;

import com.github.harboat.clients.placement.PlacementRequest;
import com.github.harboat.rabbitmq.RabbitMQMessageProducer;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlacementQueueProducer {

    private final RabbitMQMessageProducer producer;

    @Value("${rabbitmq.exchanges.placement}")
    private String internalExchange;

    @Value("${rabbitmq.routing-keys.placement}")
    private String placementRoutingKey;

    public void sendRequest(PlacementRequest placementRequest) {
        producer.publish(placementRequest, internalExchange, placementRoutingKey);
    }

}
