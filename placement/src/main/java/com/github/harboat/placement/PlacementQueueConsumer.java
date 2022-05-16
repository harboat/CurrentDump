package com.github.harboat.placement;

import com.github.harboat.clients.core.placement.PlacementRequest;
import com.github.harboat.clients.logger.GenericLog;
import com.github.harboat.clients.logger.LogType;
import com.github.harboat.clients.logger.ServiceType;
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
//        placementLogProducer.sendLog(
//                GenericLog.builder()
//                        .service(ServiceType.PLACEMENT)
//                        .type(LogType.INFO)
//                        .message("Consuming incoming request from placement queue")
//                        .content(placementRequest)
//                        .build()
//        );
        service.generate(placementRequest);
    }

}
