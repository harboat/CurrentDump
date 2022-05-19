package com.github.harboat.configuration;

import com.github.harboat.clients.notification.NotificationRequest;
import com.github.harboat.clients.rooms.RoomCreated;
import com.github.harboat.rabbitmq.RabbitMQMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlacementQueueProducer {

    private final RabbitMQMessageProducer producer;

    @Value("${rabbitmq.exchanges.game}")
    private String internalExchange;

    @Value("${rabbitmq.routing-keys.game}")
    private String gameRoutingKey;

    void sendRoom(RoomCreated roomCreated) {
        producer.publish(roomCreated, internalExchange, gameRoutingKey);
    }

}

