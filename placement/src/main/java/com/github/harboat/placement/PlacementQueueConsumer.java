package com.github.harboat.placement;

import com.github.harboat.clients.core.placement.PlacementRequest;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlacementQueueConsumer {

    private final PlacementService service;
//    private final LogProducer logProducer;

    @RabbitListener(
            queues = {"${rabbitmq.queues.placement}"}
    )
    public void consume(PlacementRequest placementRequest) {
//        logProducer.sendLog(
//            InfoLog.builder()
//                    .service(ServiceType.PLACEMENT)
//                    .message("Receiving request from placement queue")
//                    .content(placementRequest)
//                    .build()
//        );
        service.generate(placementRequest);
    }

}
