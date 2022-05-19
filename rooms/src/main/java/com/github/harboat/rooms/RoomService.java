package com.github.harboat.rooms;

import com.github.harboat.clients.configuration.ConfigurationCreate;
import com.github.harboat.clients.configuration.CreateGame;
import com.github.harboat.clients.exceptions.BadRequest;
import com.github.harboat.clients.notification.EventType;
import com.github.harboat.clients.notification.NotificationRequest;
import com.github.harboat.clients.rooms.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@AllArgsConstructor
public class RoomService {

    private RoomRepository repository;
    private CoreQueueProducer coreQueueProducer;
    private ConfigQueueProducer configQueueProducer;
    private NotificationProducer notificationProducer;

    public void create(RoomCreate roomCreate) {
        Player player = Player.builder()
                .ready(false)
                .fleetSet(false)
                .build();
        Room room = repository.save(
                Room.builder()
                        .players(Map.of(
                                roomCreate.playerId(), player
                        ))
                        .ownerId(roomCreate.playerId())
                        .started(false)
                        .build()
        );
        coreQueueProducer.sendRoom(new RoomCreated(room.getId(), roomCreate.playerId()));
        configQueueProducer.sendCreate(
                new ConfigurationCreate(room.getId(), roomCreate.playerId())
        );
    }

    @Transactional
    public void markFleetSet(MarkFleetSet markFleetSet) {
        Room room = getRoomFromRequest(markFleetSet.roomId(), markFleetSet.playerId());
        room.markPlayerFleetSet(markFleetSet.playerId());
        room.getPlayers().keySet().forEach(p -> {
            notificationProducer.sendNotification(
                    new NotificationRequest<>(p, EventType.FLEET_CREATED, markFleetSet)
            );
        });
    }

    @Transactional
    public void changeReady(ChangePlayerReadiness playerReadiness) {
        Room room = getRoomFromRequest(playerReadiness.roomId(), playerReadiness.playerId());
        if (!room.isPlayerFleetSet(playerReadiness.playerId()))
            throw new BadRequest("Player fleet is not set yet, you can't change readiness!");
        room.changePlayerReadiness(playerReadiness.playerId());
        room.getPlayers().keySet().forEach(p -> {
            notificationProducer.sendNotification(
                    new NotificationRequest<>(p, EventType.PLAYER_READY, playerReadiness)
            );
        });
    }

    @Transactional
    public void markStart(MarkStart markStart) {
        Room room = getRoomFromRequest(markStart.roomId(), markStart.playerId());
        if (!room.isPlayerAnOwner(markStart.playerId())) throw new BadRequest("You are not an owner of this game!");
        if (!room.areAllPlayersReady()) throw new BadRequest("Not all players are ready!");
        if (!room.areAllFleetsSet()) throw new BadRequest("Not all players have fleet set!");
        room.setStarted(true);
        coreQueueProducer.sendStart(new RoomGameStart(markStart.roomId()));
        configQueueProducer.sendCreateGame(
                new CreateGame(room.getId(), markStart.playerId())
        );
    }

    private Room getRoomFromRequest(String roomId, String playerId) {
        Room room = repository.findById(roomId).orElseThrow();
        if (!room.isPlayerInTheRoom(playerId)) throw new BadRequest("Player is not in the game!");
        return room;
    }
}
