package com.github.harboat.placement;

import com.github.harboat.clients.logger.PlacementLog;
import com.github.harboat.rabbitmq.RabbitMQMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
public class PlacementLogProducer {

    private final RabbitMQMessageProducer producer;

    @Value("${rabbitmq.exchanges.logger}")
    private String internalExchange;

    @Value("${rabbitmq.routing-keys.logger}")
    private String logRoutingKey;

    <T extends PlacementLog> void sendLog(T log) {
        producer.publish(log, internalExchange, logRoutingKey);
    }

}
