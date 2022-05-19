package com.github.harboat.configuration;

import com.github.harboat.clients.notification.NotificationRequest;
import com.github.harboat.clients.rooms.MarkFleetSet;
import com.github.harboat.rabbitmq.RabbitMQMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomsQueueProducer {

    private final RabbitMQMessageProducer producer;

    @Value("${rabbitmq.exchanges.rooms}")
    private String internalExchange;

    @Value("${rabbitmq.routing-keys.rooms}")
    private String roomsRoutingKey;

    public void sendPlacement(MarkFleetSet markFleetSet) {
        producer.publish(markFleetSet, internalExchange, roomsRoutingKey);
    }
}

