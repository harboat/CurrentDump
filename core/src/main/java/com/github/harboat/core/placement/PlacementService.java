package com.github.harboat.core.placement;

import com.github.harboat.clients.core.board.Size;
import com.github.harboat.clients.core.placement.PlacementRequest;
import com.github.harboat.clients.exceptions.BadRequest;
import com.github.harboat.core.games.GameUtility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PlacementService {

    private PlacementQueueProducer producer;
    private GameUtility gameUtility;

    public void palaceShips(String gameId, String playerId) {
        Optional<Size> size = gameUtility.getGameSizeForUser(gameId, playerId);
        if (size.isEmpty()) throw new BadRequest("Game not found for the user!");
        producer.sendRequest(
                new PlacementRequest(gameId, playerId, size.get())
        );
    }
}
