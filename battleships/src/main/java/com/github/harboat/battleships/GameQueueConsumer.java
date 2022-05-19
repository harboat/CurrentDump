package com.github.harboat.battleships;

import com.github.harboat.battleships.board.BoardService;
import com.github.harboat.battleships.fleet.FleetService;
import com.github.harboat.battleships.game.GameService;
import com.github.harboat.battleships.shot.ShotService;
import com.github.harboat.clients.core.board.BoardCreation;
import com.github.harboat.clients.core.game.GameCreation;
import com.github.harboat.clients.core.game.GameStart;
import com.github.harboat.clients.core.game.PlayerJoin;
import com.github.harboat.clients.core.placement.GamePlacement;
import com.github.harboat.clients.core.shot.NukeShotRequest;
import com.github.harboat.clients.core.shot.ShotRequest;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@RabbitListener(
        queues = {"${rabbitmq.queues.game}"}
)
public class GameQueueConsumer {

    private GameService gameService;
    private BoardService boardService;
    private FleetService fleetService;
    private ShotService shotService;

    @RabbitHandler
    public void consume(GameCreation gameCreation) {
        gameService.createGame(gameCreation);
    }

    @RabbitHandler
    public void consume(BoardCreation boardCreation) {
        boardService.createBoard(boardCreation);
    }

    @RabbitHandler
    public void consume(GamePlacement gamePlacement) {
        fleetService.create(gamePlacement);
    }

    @RabbitHandler
    public void consume(PlayerJoin playerJoin) {
        gameService.playerJoin(playerJoin);
    }

    @RabbitHandler
    public void consumer(ShotRequest shotRequest) {
        shotService.takeAShoot(shotRequest);
    }

    @RabbitHandler
    public void consumer(NukeShotRequest nukeShotRequest) {
        shotService.takeAShoot(nukeShotRequest);
    }

    @RabbitHandler
    public void consumer(GameStart gameStart) {
        gameService.start(gameStart);
    }

}
