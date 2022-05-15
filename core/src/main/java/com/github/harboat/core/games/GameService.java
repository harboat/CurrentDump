package com.github.harboat.core.games;

import com.github.harboat.clients.core.board.Size;
import com.github.harboat.clients.core.game.GameCreation;
import com.github.harboat.clients.core.game.GameCreationResponse;
import com.github.harboat.clients.exceptions.BadRequest;
import com.github.harboat.clients.exceptions.ResourceNotFound;
import com.github.harboat.clients.notification.EventType;
import com.github.harboat.core.GameQueueProducer;
import com.github.harboat.core.websocket.Event;
import com.github.harboat.core.websocket.WebsocketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GameService {

    private GameRepository repository;
    private GameQueueProducer producer;
    private WebsocketService websocketService;

    public void create(String playerId) {
        producer.sendRequest(
                new GameCreation(playerId)
        );
    }

    public void create(GameCreationResponse creationResponse) {
        Game game = repository.save(
                Game.builder()
                        .gameId(creationResponse.gameId())
                        .players(List.of(creationResponse.playerId()))
                        .ownerId(creationResponse.playerId())
                        .started(false)
                        .build()
        );
        websocketService.notifyFrontEnd(
                creationResponse.playerId(),
                new Event<>(EventType.GAME_CREATED, game)
        );
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

    public void setBoardSizeForGame(String gameId, Size size) {
        Game game = repository.findByGameId(gameId).orElseThrow();
        game.setSize(size);
        repository.save(game);
    }

    public void join(String playerId, String gameId) {
        Game game = repository.findByGameId(gameId).orElseThrow(() -> new ResourceNotFound("Game not found!"));
        if (game.getPlayers().contains(playerId)) throw new BadRequest("You are already in this game!");
    }
}
