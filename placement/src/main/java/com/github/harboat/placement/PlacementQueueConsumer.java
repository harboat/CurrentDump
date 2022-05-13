package com.github.harboat.placement;

import com.github.harboat.clients.placement.PlacementRequest;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlacementQueueConsumer {

    private final PlacementService service;

    @RabbitListener(
            queues = {"${rabbitmq.queues.placement}"}
    )
    public void consume(PlacementRequest placementRequest) {
        service.generate(placementRequest);
    }

}
