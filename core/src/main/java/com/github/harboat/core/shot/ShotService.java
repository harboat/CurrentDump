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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class ShotService {
    private GameQueueProducer producer;
    private GameUtility gameUtility;
    private WebsocketService websocketService;

    public void takeAShoot(String gameId, String playerId, Integer cellId) {
        var game = gameUtility.findByGameId(gameId)
                .orElseThrow(() -> new ResourceNotFound("Game not found!"));
        if (!game.getPlayers().contains(playerId)) throw new BadRequest("Player's not in the game!");
        producer.sendRequest(
                new ShotRequest(gameId, playerId, cellId)
        );
    }

    public void takeAShoot(ShotResponse shotResponse) {
        String enemyId = gameUtility.getEnemyId(shotResponse.gameId(), shotResponse.playerId()).orElseThrow();
        websocketService.notifyFrontEnd(
                shotResponse.playerId(), new Event<>(EventType.HIT, shotResponse)
        );
        websocketService.notifyFrontEnd(
                enemyId, new Event<>(EventType.HIT, shotResponse)
        );
    }
}
