package com.github.harboat.logger;

import com.github.harboat.rabbitmq.RabbitMQMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogProducer {

    private final RabbitMQMessageProducer producer;

    @Value("${rabbitmq.exchanges.logger}")
    private String internalExchange;

    @Value("${rabbitmq.routing-keys.logger}")
    private String logRoutingKey;

    <T extends Log> void sendLog(T log) {
        producer.publish(log, internalExchange, logRoutingKey);
    }

}
