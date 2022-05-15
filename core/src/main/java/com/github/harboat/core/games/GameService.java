package com.github.harboat.core.games;

import com.github.harboat.clients.core.board.Size;
import com.github.harboat.clients.core.game.GameCreation;
import com.github.harboat.clients.core.game.GameCreationResponse;
import com.github.harboat.clients.core.game.PlayerJoin;
import com.github.harboat.clients.core.game.PlayerJoinedResponse;
import com.github.harboat.clients.exceptions.BadRequest;
import com.github.harboat.clients.exceptions.ResourceNotFound;
import com.github.harboat.clients.notification.EventType;
import com.github.harboat.core.GameQueueProducer;
import com.github.harboat.core.placement.PlacementService;
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
    private PlacementService placementService;
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



    public void setBoardSizeForGame(String gameId, Size size) {
        Game game = repository.findByGameId(gameId).orElseThrow();
        game.setSize(size);
        repository.save(game);
    }

    public void join(String playerId, String gameId) {
        Game game = repository.findByGameId(gameId).orElseThrow(() -> new ResourceNotFound("Game not found!"));
        if (game.getPlayers().contains(playerId)) throw new BadRequest("You are already in this game!");
        producer.sendRequest(
                new PlayerJoin(gameId, playerId)
        );
    }

    public void playerJoined(PlayerJoinedResponse playerJoinedResponse) {
        Game game = repository.findByGameId(playerJoinedResponse.gameId()).orElseThrow();
        Collection<String> players = game.getPlayers();
        players.add(playerJoinedResponse.playerId());
        game.setStarted(true);
        game.setPlayers(players);
        repository.save(game);

        placementService.palaceShips(game.getGameId(), playerJoinedResponse.playerId());
        websocketService.notifyFrontEnd(
                playerJoinedResponse.playerId(),
                new Event<>(EventType.GAME_JOINED, playerJoinedResponse)
        );
        websocketService.notifyFrontEnd(
                game.getOwnerId(),
                new Event<>(EventType.ENEMY_JOIN, playerJoinedResponse)
        );
    }
}
