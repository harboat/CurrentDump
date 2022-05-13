package com.github.harboat.logger;

import com.github.harboat.clients.logger.MainServiceLog;
import com.github.harboat.clients.logger.PlacementServiceLog;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogQueueConsumer {

    private final LogService service;

    @RabbitListener(
            queues = {"${rabbitmq.queues.main-logger}"}
    )
    public void consume(PlacementServiceLog log) {
        service.log(log);
    }

    @RabbitListener(
            queues = {"${rabbitmq.queues.placement-logger}"}
    )
    public void consume(MainServiceLog log) {
        service.log(log);
    }
}
