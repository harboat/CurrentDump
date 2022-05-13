package com.github.harboat.core.board;

import com.github.harboat.clients.battleships.GameCreationRequest;
import com.github.harboat.clients.board.Size;
import com.github.harboat.core.games.GameCreationQueueProducer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BoardService {

    private GameCreationQueueProducer producer;

    public Size create(String playerId, Size size) {
        producer.sendRequest(
                new GameCreationRequest(playerId, size)
        );
        return size;
    }

}
