package com.github.harboat.battleships.board;

import com.github.harboat.battleships.CoreQueueProducer;
import com.github.harboat.clients.core.board.BoardCreation;
import com.github.harboat.clients.core.board.BoardCreationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class BoardService {

    private BoardRepository repository;
    private CoreQueueProducer producer;

    public void createBoard(BoardCreation boardCreation) {
        Board board = Board.builder()
                .gameId(boardCreation.gameId())
                .playerId(boardCreation.playerId())
                .size(boardCreation.size())
                .cells(initCells(boardCreation.size().width(), boardCreation.size().height()))
                .build();
        repository.save(board);
        producer.sendResponse(
                new BoardCreationResponse(boardCreation.gameId(), board.getPlayerId(), boardCreation.size())
        );
    }

    private Map<Integer, Cell> initCells(int width, int height) {
        return IntStream.rangeClosed(1, width * height).boxed()
                .collect(Collectors.toMap((i) -> i, (i) -> Cell.WATER));
    }

    // TODO: Error handling
    public void markOccupied(String gameId, String playerId, Collection<Integer> cells) {
        Board board = repository.findByGameIdAndPlayerId(gameId, playerId).orElseThrow();
        Map<Integer, Cell> currentState = board.getCells();
        cells.forEach(c -> currentState.put(c, Cell.OCCUPIED));
        repository.save(board);
    }

    public void createBoardForSecondPlayer(String gameId, String playerId) {
        Board board = repository.findByGameId(gameId).orElseThrow();
        createBoard(new BoardCreation(gameId, playerId, board.getSize()));
    }

    // TODO: Error handling
    public void markHit(String gameId, String username, Integer cellId) {
        var board = repository.findByGameIdAndPlayerId(gameId, username).orElseThrow();
        var currentState = board.getCells();
        currentState.put(cellId, Cell.HIT);
        repository.save(board);
    }
}
