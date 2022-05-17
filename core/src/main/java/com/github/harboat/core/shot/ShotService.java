package com.github.harboat.core.shot;

import com.github.harboat.clients.core.shot.ShotRequest;
import com.github.harboat.clients.core.shot.ShotResponse;
import com.github.harboat.clients.exceptions.BadRequest;
import com.github.harboat.clients.exceptions.ResourceNotFound;
import com.github.harboat.clients.notification.EventType;
import com.github.harboat.core.GameQueueProducer;
import com.github.harboat.core.games.GameUtility;
import com.github.harboat.core.websocket.Event;
import com.github.harboat.core.websocket.WebsocketService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShotService {
    private GameQueueProducer producer;
    private GameUtility gameUtility;
    private WebsocketService websocketService;


//    @Async("shotServiceProducerThreads")
    public void takeAShoot(String gameId, String playerId, Integer cellId) {
        var game = gameUtility.findByGameId(gameId)
                .orElseThrow(() -> new ResourceNotFound("Game not found!"));
        if (!game.getPlayers().contains(playerId)) throw new BadRequest("Player is not in the game!");
        if (!game.getStarted()) throw new BadRequest("Game has not started!");
        if (!game.getPlayerTurn().equals(playerId)) throw new BadRequest("It is not your turn!");
        producer.sendRequest(
                new ShotRequest(gameId, playerId, cellId)
        );
    }

//    @Async("shotServiceProducerThreads")
    public void takeAShoot(ShotResponse shotResponse) {
        String playerId = shotResponse.gameId();
        String enemyId = gameUtility.switchTurnAndGetEnemyId(shotResponse.gameId(), playerId);
        websocketService.notifyFrontEnd(
                playerId, new Event<>(EventType.HIT, new ShotResult(playerId, shotResponse.cells()))
        );
        websocketService.notifyFrontEnd(
                enemyId, new Event<>(EventType.HIT, new ShotResult(playerId, shotResponse.cells()))
        );
    }
}
