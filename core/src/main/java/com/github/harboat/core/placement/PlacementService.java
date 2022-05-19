package com.github.harboat.core.placement;

import com.github.harboat.clients.core.board.Size;
import com.github.harboat.clients.core.placement.PlacementRequest;
import com.github.harboat.clients.core.placement.PlacementResponse;
import com.github.harboat.clients.exceptions.BadRequest;
import com.github.harboat.clients.notification.EventType;
import com.github.harboat.clients.placement.GeneratePlacement;
import com.github.harboat.core.games.FleetPlaced;
import com.github.harboat.core.games.Game;
import com.github.harboat.core.games.GameUtility;
import com.github.harboat.core.rooms.Room;
import com.github.harboat.core.rooms.RoomRepository;
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
    private RoomRepository repository;

    public void palaceShips(String roomId, String playerId) {
        Room room = repository.findByRoomId(roomId).orElseThrow();
        producer.send(
                new GeneratePlacement(roomId, playerId, room.getSize())
        );
    }
}
