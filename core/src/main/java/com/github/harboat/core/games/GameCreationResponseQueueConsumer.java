package com.github.harboat.core.games;

import com.github.harboat.clients.core.GameCreationResponse;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GameCreationResponseQueueConsumer {

    private GameService gameService;

    @RabbitListener(
            queues = {"${rabbitmq.queues.game-creation-response}"}
    )
    void consume(GameCreationResponse response) {
        gameService.create(response.gameId(), response.playerId(), response.size());
    }

}
