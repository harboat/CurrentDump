package com.github.harboat.core.placement;

import com.github.harboat.clients.core.board.Size;
import com.github.harboat.clients.core.placement.PlacementRequest;
import com.github.harboat.clients.core.placement.PlacementResponse;
import com.github.harboat.clients.exceptions.BadRequest;
import com.github.harboat.clients.notification.EventType;
import com.github.harboat.core.games.FleetPlaced;
import com.github.harboat.core.games.Game;
import com.github.harboat.core.games.GameUtility;
import com.github.harboat.core.websocket.Event;
import com.github.harboat.core.websocket.WebsocketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
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

    public void placeShips(PlacementResponse placementResponse) {
        gameUtility.markFleetSet(placementResponse.gameId());
    }
}
