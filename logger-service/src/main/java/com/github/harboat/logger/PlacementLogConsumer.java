package com.github.harboat.logger;

//import com.github.harboat.clients.logger.InfoLog;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@RabbitListener(
        queues = "${rabbitmq.queues.placement-logger}"
)
public class PlacementLogConsumer {

//    private PlacementLogService service;
//
//    @RabbitHandler
//    public void consume(InfoLog<?> infoLog) {
//        service.log(infoLog);
//    }

}
