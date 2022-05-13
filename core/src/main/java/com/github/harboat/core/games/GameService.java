package com.github.harboat.core.games;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GameService {

    private GameRepository repository;

    public void create(String gameId, String playerId) {
        repository.save(
                Game.builder()
                        .gameId(gameId)
                        .players(List.of(playerId))
                        .started(false)
                        .build()
        );
    }

    public boolean isPlayerInGame(String gameId, String playerId) {
        return repository.findGameByGameIdAndPlayersContains(gameId, playerId).isPresent();
    }

}
