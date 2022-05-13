package com.github.harboat.core.placement;

import com.github.harboat.clients.board.Size;
import com.github.harboat.clients.exceptions.BadRequest;
import com.github.harboat.clients.placement.PlacementRequest;
import com.github.harboat.core.games.GameService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PlacementService {

    private GameService gameService;
    private PlacementQueueProducer producer;

    public void palaceShips(String gameId, String playerId) {
        Optional<Size> size = gameService.getGameSize(gameId, playerId);
        if (size.isEmpty()) throw new BadRequest("Game not found for the user!");
        producer.sendRequest(
                new PlacementRequest(gameId, playerId, size.get())
        );
    }
}
