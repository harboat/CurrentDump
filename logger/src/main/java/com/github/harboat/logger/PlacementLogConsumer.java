package com.github.harboat.logger;

import com.github.harboat.clients.logger.GenericLog;
import com.github.harboat.clients.logger.ShipsGeneratedLog;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@RabbitListener(
        queues = "${rabbitmq.queues.placement-logger}"
)
public class PlacementLogConsumer {

    private PlacementLogService service;

    @RabbitHandler
    public void consume(GenericLog<?> genericLog) {
        service.log(genericLog);
    }

    @RabbitHandler
    public void consume(ShipsGeneratedLog generatedLog) {
        service.log(generatedLog);
    }

}
