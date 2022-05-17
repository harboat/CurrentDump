package com.github.harboat.core.games;

import com.github.harboat.clients.core.board.Size;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GameUtility {

    private GameRepository repository;

    public void setBoardSizeForGame(String gameId, Size size) {
        Game game = repository.findByGameId(gameId).orElseThrow();
        game.setSize(size);
        repository.save(game);
    }

    public Collection<String> getNotStartedGamesIdsForUser(String playerId) {
        return repository.findGamesByOwnerIdAndNotStarted(playerId).stream()
                .map(Game::getGameId)
                .toList();
    }

    public Optional<Size> getGameSizeForUser(String gameId, String playerId) {
        Optional<Game> game = repository.findGameByGameIdAndPlayersContains(gameId, playerId);
        if (game.isEmpty() || game.get().getSize() == null) return Optional.empty();
        return Optional.of(game.get().getSize());
    }

    public Optional<Game> findByGameId(String gameId) {
        return repository.findByGameId(gameId);
    }

    public void markFleetSet(String gameId) {
        Game game = repository.findByGameId(gameId).orElseThrow();
        Collection<Boolean> feelWasSet = game.getFeelWasSet();
        feelWasSet.remove(false);
        feelWasSet.add(true);
        repository.save(game);
    }

    public void switchTurn(String gameId, String enemyId) {
        Game game = repository.findByGameId(gameId).orElseThrow();
        game.setPlayerTurn(enemyId);
        repository.save(game);
    }

    public String getEnemyId(String gameId, String playerId) {
        Game game = repository.findByGameId(gameId).orElseThrow();
        return game
                .getPlayers().stream()
                .dropWhile(s -> s.equals(playerId))
                .findAny().orElseThrow();
    }
}
