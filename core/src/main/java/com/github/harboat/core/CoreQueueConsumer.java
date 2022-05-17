package com.github.harboat.core;

import com.github.harboat.clients.core.board.BoardCreationResponse;
import com.github.harboat.clients.core.game.GameCreationResponse;
import com.github.harboat.clients.core.game.PlayerJoinedResponse;
import com.github.harboat.clients.core.shot.PlayerWon;
import com.github.harboat.clients.core.shot.ShotResponse;
import com.github.harboat.core.board.BoardService;
import com.github.harboat.core.games.GameService;
import com.github.harboat.core.shot.ShotService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@RabbitListener(
        queues = {"${rabbitmq.queues.core}"}
)
public class CoreQueueConsumer {

    private GameService gameService;
    private BoardService boardService;
    private ShotService shotService;

    @RabbitHandler
    @Async("coreQueueConsumerThreads")
    public void consume(GameCreationResponse creationResponse) {
        gameService.create(creationResponse);
    }

    @RabbitHandler
    @Async("coreQueueConsumerThreads")
    public void consume(BoardCreationResponse creationResponse) {
        boardService.create(creationResponse);
    }

    @RabbitHandler
    @Async("coreQueueConsumerThreads")
    public void consume(PlayerJoinedResponse playerJoinedResponse) {
        gameService.playerJoined(playerJoinedResponse);
    }

    @RabbitHandler
    @Async("coreQueueConsumerThreads")
    public void consume(ShotResponse shotResponse) {
        shotService.takeAShoot(shotResponse);
    }

    @RabbitHandler
    public void consume(PlayerWon playerWon) {
        gameService.endGame(playerWon);
    }

}
