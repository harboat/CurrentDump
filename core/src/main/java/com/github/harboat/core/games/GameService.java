package com.github.harboat.core.games;

import com.github.harboat.clients.board.Size;
import com.github.harboat.clients.notification.EventType;
import com.github.harboat.core.websocket.Event;
import com.github.harboat.core.websocket.WebsocketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GameService {

    private GameRepository repository;
    private WebsocketService websocketService;

    public void create(String gameId, String playerId, Size size) {
        Game game = repository.save(
                Game.builder()
                        .gameId(gameId)
                        .size(size)
                        .players(List.of(playerId))
                        .started(false)
                        .build()
        );
        // todo: create dto
        websocketService.notifyFrontEnd(
                playerId,
                new Event<>(EventType.GAME_CREATED, game)
        );
    }

    public Optional<Size> getGameSize(String gameId, String playerId) {
        Optional<Game> game = repository.findGameByGameIdAndPlayersContains(gameId, playerId);
        if (game.isEmpty()) return Optional.empty();
        return Optional.of(game.get().getSize());
    }

}
