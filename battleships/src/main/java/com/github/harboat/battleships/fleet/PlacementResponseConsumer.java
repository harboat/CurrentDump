package com.github.harboat.battleships.fleet;

import com.github.harboat.clients.placement.PlacementResponse;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PlacementResponseConsumer {

    private FleetService service;

    @RabbitListener(
            queues = {"${rabbitmq.queues.placement-response}"}
    )
    void consume(PlacementResponse response) {
        service.create(response);
    }

}
