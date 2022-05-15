package com.github.harboat.core.shot;

import com.github.harboat.clients.exceptions.BadRequest;
import com.github.harboat.clients.exceptions.ResourceNotFound;
import com.github.harboat.core.GameQueueProducer;
import com.github.harboat.core.games.GameUtility;
import org.springframework.stereotype.Service;

@Service
public class ShotService {

    private GameQueueProducer producer;
    private GameUtility gameUtility;

    public void takeAShoot(String gameId, String username, Integer cellId) {
        var game = gameUtility.findByGameId(gameId)
                .orElseThrow(() -> new ResourceNotFound("Game not found!"));
        if (!game.getPlayers().contains(username)) throw new BadRequest("Player's not in the game!");
        Shot shot = Shot.builder()
                .gameId(gameId)
                .username(username)
                .cellId(cellId)
                .build();
        producer.sendRequest(shot);
    }
}
