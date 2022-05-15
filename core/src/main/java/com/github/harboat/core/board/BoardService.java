package com.github.harboat.core.board;

import com.github.harboat.clients.core.board.BoardCreation;
import com.github.harboat.clients.core.board.BoardCreationResponse;
import com.github.harboat.clients.core.board.Size;
import com.github.harboat.clients.exceptions.BadRequest;
import com.github.harboat.clients.notification.EventType;
import com.github.harboat.core.GameQueueProducer;
import com.github.harboat.core.games.GameService;
import com.github.harboat.core.websocket.Event;
import com.github.harboat.core.websocket.WebsocketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BoardService {

    private final GameQueueProducer producer;
    private final GameService gameService;
    private final WebsocketService service;

    public void create(String gameId, String playerId, Size size) {
        // todo: better error
        if (!gameService.getNotStartedGamesIdsForUser(playerId).contains(gameId))
            throw new BadRequest("You are not in this game!");
        producer.sendRequest(
                new BoardCreation(gameId, playerId, size)
        );
    }

    public void create(BoardCreationResponse response) {
        gameService.setBoardSizeForGame(response.gameId(), response.size());
        service.notifyFrontEnd(
                response.playerId(),
                new Event<>(EventType.BOARD_CREATED, response.size())
        );
    }

}
