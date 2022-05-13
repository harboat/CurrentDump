package com.github.harboat.battleships.game;

import com.github.harboat.battleships.board.BoardService;
import com.github.harboat.clients.board.Size;
import com.github.harboat.clients.core.GameCreationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class GameCreationService {

    private GameRepository repository;
    private BoardService boardService;
    private GameCreationResponseProducer producer;

    @Transactional
    public void createGame(String playerId, Size boardSize) {
        Game game = Game.builder()
                .playerIds(List.of(playerId))
                .turnOfPlayer(playerId)
                .build();
        game = repository.save(game);
        boardService.createBoard(game.getId(), playerId, boardSize);
        producer.sendResponse(
                new GameCreationResponse(game.getId(), playerId, boardSize)
        );
    }
}
