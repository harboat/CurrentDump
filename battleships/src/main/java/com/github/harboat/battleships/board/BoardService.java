package com.github.harboat.battleships.board;

import com.github.harboat.clients.board.Size;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class BoardService {

    private final BoardRepository repository;

    public Size createBoard(String userId, String gameId, Size size) {
        Board board = Board.builder()
                .gameId(gameId)
                .playerId(userId)
                .size(size)
                .cells(initCells(size.width(), size.height()))
                .build();
        repository.save(board);
        return size;
    }

    private Map<Integer, Cell> initCells(int width, int height) {
        return IntStream.rangeClosed(1, width * height).boxed()
                .collect(Collectors.toMap((i) -> i, (i) -> Cell.WATER));
    }
}
