package com.github.harboat.placement;

import com.github.harboat.clients.configuration.SetShipsPosition;
import com.github.harboat.clients.core.placement.GamePlacement;
import com.github.harboat.rabbitmq.RabbitMQMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfigQueueProducer {

    private final RabbitMQMessageProducer producer;

    @Value("${rabbitmq.exchanges.config}")
    private String internalExchange;

    @Value("${rabbitmq.routing-keys.config}")
    private String configRoutingKey;

    void sendPlacement(SetShipsPosition setShipsPosition) {
        producer.publish(setShipsPosition, internalExchange, configRoutingKey);
    }

}
