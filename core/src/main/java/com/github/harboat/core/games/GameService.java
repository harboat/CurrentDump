package com.github.harboat.core.games;

import com.github.harboat.clients.core.game.*;
import com.github.harboat.clients.core.shot.PlayerWon;
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
        repository.save(
                Game.builder()
                        .gameId(creationResponse.gameId())
                        .players(List.of(creationResponse.playerId()))
                        .ownerId(creationResponse.playerId())
                        .playerTurn(creationResponse.playerId())
                        .fleetWasSet(List.of(false, false))
                        .started(false)
                        .ended(false)
                        .build()
        );
        websocketService.notifyFrontEnd(
                creationResponse.playerId(),
                new Event<>(
                        EventType.GAME_CREATED,
                        new GameCreated(creationResponse.gameId())
                )
        );
    }

    public void join(String playerId, String gameId) {
        Game game = repository.findByGameId(gameId).orElseThrow(() -> new ResourceNotFound("Game not found!"));
        if (game.getPlayers().contains(playerId)) throw new BadRequest("You are already in this game!");
        if (game.getStarted()) throw new BadRequest("Game already started!");
        if (game.getEnded()) throw new BadRequest("Game has ended!");
        producer.sendRequest(
                new PlayerJoin(gameId, playerId)
        );
    }

    public void playerJoined(PlayerJoinedResponse playerJoinedResponse) {
        Game game = repository.findByGameId(playerJoinedResponse.gameId()).orElseThrow();
        Collection<String> players = game.getPlayers();
        players.add(playerJoinedResponse.playerId());
        game.setPlayers(players);
        repository.save(game);
        String gameId = playerJoinedResponse.gameId();
        String joinedPlayerId = playerJoinedResponse.playerId();
        String enemyId = game.getOwnerId();
        websocketService.notifyFrontEnd(
                joinedPlayerId,
                new Event<>(
                        EventType.GAME_JOINED,
                        new PlayerJoined(gameId, joinedPlayerId, enemyId)
                )
        );
        websocketService.notifyFrontEnd(
                enemyId,
                new Event<>(
                        EventType.GAME_JOINED,
                        new PlayerJoined(gameId, enemyId, joinedPlayerId)
                )
        );
    }

    public void start(String playerId, String gameId) {
        Game game = repository.findByGameId(gameId).orElseThrow();
        if (!game.getOwnerId().equals(playerId)) throw new BadRequest("You are not an owner of this lobby!");
        if (game.getFleetWasSet().contains(false)) throw new BadRequest("Game is not ready!");
        if (game.getStarted()) throw new BadRequest("Game already started!");
        producer.sendRequest(
                new GameStart(gameId)
        );
    }

    public void start(GameStartResponse response) {
        Game game = repository.findByGameId(response.gameId()).orElseThrow();
        game.setStarted(true);
        game.setPlayerTurn(response.playerTurn());
        repository.save(game);
        game.getPlayers().forEach(p ->
                websocketService.notifyFrontEnd(
                        p,
                        new Event<>(EventType.GAME_STARTED, new GameStarted(response.playerTurn()))
                )
        );
    }

    public void forfeit(String playerId, String gameId) {
        Game game = repository.findByGameId(gameId).orElseThrow();
        if (!game.getPlayers().contains(playerId)) throw new BadRequest("You are not in this game");
        String enemyId = game.getPlayers().stream()
                .dropWhile(p -> p.equals(playerId))
                .findFirst().orElseThrow();
        endGame(new PlayerWon(gameId, enemyId));
    }

    public void endGame(PlayerWon playerWon) {
        Game game = repository.findByGameId(playerWon.gameId()).orElseThrow();
        game.setEnded(true);
        repository.save(game);
        game.getPlayers().forEach(p ->
                websocketService.notifyFrontEnd(
                        p,
                        new Event<>(EventType.GAME_END, new GameEnded(playerWon.playerId()))
                )
        );
    }

}
