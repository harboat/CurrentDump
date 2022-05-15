package com.github.harboat.placement;

import com.github.harboat.clients.core.placement.PlacementRequest;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlacementQueueConsumer {

    private final PlacementService service;
    private final PlacementLogProducer placementLogProducer;

    @RabbitListener(
            queues = {"${rabbitmq.queues.placement}"}
    )
    public void consume(PlacementRequest placementRequest) {
        placementLogProducer.sendLog(

        );
        service.generate(placementRequest);
    }

}
