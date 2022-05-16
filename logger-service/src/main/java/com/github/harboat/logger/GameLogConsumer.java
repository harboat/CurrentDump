package com.github.harboat.logger;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@RabbitListener(
        queues = "${rabbitmq.queues.game-logger}"
)
public class GameLogConsumer {

    private PlacementLogService service;

}
