package com.github.harboat.core.games;

import com.github.harboat.clients.core.board.Size;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class GameUtility {

    private GameRepository repository;

    public Optional<Size> getGameSizeForUser(String gameId, String playerId) {
        Optional<Game> game = repository.findGameByGameIdAndPlayersContains(gameId, playerId);
        if (game.isEmpty() || game.get().getSize() == null) return Optional.empty();
        return Optional.of(game.get().getSize());
    }

    public Optional<Game> findByGameId(String gameId) {
        return repository.findByGameId(gameId);
    }

    public Optional<String> getEnemyId(String gameId, String playerId) {
        Game game = repository.findByGameId(gameId).orElseThrow();
        return game
                .getPlayers().stream()
                .dropWhile(s -> s.equals(playerId))
                .findAny();
    }


}
