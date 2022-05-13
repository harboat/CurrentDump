package com.github.harboat.battleships.board;

import com.github.harboat.clients.board.Size;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class BoardService {

    private final BoardRepository repository;

    public Board createBoard(String gameId, String playerId, Size size) {
        Board board = Board.builder()
                .gameId(gameId)
                .playerId(playerId)
                .size(size)
                .cells(initCells(size.width(), size.height()))
                .build();
        return repository.save(board);
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

}
