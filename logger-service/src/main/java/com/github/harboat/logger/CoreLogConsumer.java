package com.github.harboat.logger;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@RabbitListener(
        queues = "${rabbitmq.queues.core-logger}"
)
public class CoreLogConsumer {

    private PlacementLogService service;

}
