package com.github.harboat.placement;

import com.github.harboat.clients.core.placement.PlacementRequest;
import com.github.harboat.clients.placement.GeneratePlacement;
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
    public void consume(GeneratePlacement generatePlacement) {
        service.generate(generatePlacement);
    }

}
