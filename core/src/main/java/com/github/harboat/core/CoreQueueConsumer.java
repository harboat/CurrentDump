package com.github.harboat.core;

import com.github.harboat.clients.core.board.BoardCreationResponse;
import com.github.harboat.clients.core.game.GameCreationResponse;
import com.github.harboat.core.board.BoardService;
import com.github.harboat.core.games.GameService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@RabbitListener(
        queues = {"${rabbitmq.queues.core}"}
)
public class CoreQueueConsumer {

    private GameService gameService;
    private BoardService boardService;

    @RabbitHandler
    public void consume(GameCreationResponse creationResponse) {
        gameService.create(creationResponse);
    }

    @RabbitHandler
    public void consume(BoardCreationResponse creationResponse) {
        boardService.create(creationResponse);
    }

}
