package com.github.harboat.battleships.game;

import com.github.harboat.clients.battleships.GameCreationRequest;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GameCreationQueueConsumer {

    private GameCreationService service;

    @RabbitListener(
            queues = {"${rabbitmq.queues.game-creation}"}
    )
    void consume(GameCreationRequest request) {
        service.createGame(request.playerId(), request.size());
    }

}
