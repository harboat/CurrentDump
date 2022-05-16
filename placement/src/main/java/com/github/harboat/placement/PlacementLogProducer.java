package com.github.harboat.placement;

import com.github.harboat.clients.logger.Log;
import com.github.harboat.clients.logger.PlacementLog;
import com.github.harboat.rabbitmq.RabbitMQMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimpleResourceHolder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
public class PlacementLogProducer {

    private final RabbitMQMessageProducer producer;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchanges.logger}")
    private String internalExchange;

    @Value("${rabbitmq.routing-keys.logger}")
    private String logRoutingKey;

    <T extends Log> void sendLog(T log) {
        SimpleResourceHolder.bind(rabbitTemplate.getConnectionFactory(), "logger");
        try {
            rabbitTemplate.convertAndSend(internalExchange, logRoutingKey, log);
        } finally {
            SimpleResourceHolder.unbind(rabbitTemplate.getConnectionFactory());
        }
    }

}
