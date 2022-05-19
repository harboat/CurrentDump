package com.github.harboat.core;

import com.github.harboat.clients.configuration.SetGameSize;
import com.github.harboat.clients.core.board.BoardCreationResponse;
import com.github.harboat.clients.core.game.GameCreationResponse;
import com.github.harboat.clients.core.game.GameStartResponse;
import com.github.harboat.clients.core.game.PlayerJoinedResponse;
import com.github.harboat.clients.core.placement.PlacementResponse;
import com.github.harboat.clients.core.shot.PlayerWon;
import com.github.harboat.clients.core.shot.ShotResponse;
import com.github.harboat.clients.rooms.RoomCreated;
import com.github.harboat.clients.rooms.RoomGameStart;
import com.github.harboat.core.board.BoardService;
import com.github.harboat.core.configuration.ConfigurationService;
import com.github.harboat.core.games.GameService;
import com.github.harboat.core.placement.PlacementService;
import com.github.harboat.core.rooms.RoomService;
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

    private RoomService roomService;
    private ConfigurationService configurationService;

    @RabbitHandler
    @Async("coreQueueConsumerThreads")
    public void consume(RoomCreated roomCreated) {
        roomService.create(roomCreated);
    }

    @RabbitHandler
    @Async("coreQueueConsumerThreads")
    public void consume(RoomGameStart roomGameStart) {
        roomService.start(roomGameStart);
    }

    @RabbitHandler
    @Async("coreQueueConsumerThreads")
    public void consume(SetGameSize setGameSize) {
        configurationService.setSize(setGameSize);
    }
}
