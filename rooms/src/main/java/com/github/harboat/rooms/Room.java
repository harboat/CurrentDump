package com.github.harboat.rooms;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
@Builder
public class Room {
    @Id
    private String id;
    private Map<String, Player> players;
    private String ownerId;
    private Boolean started;

    boolean isPlayerInTheRoom(String playerId) {
        return players.containsKey(playerId);
    }

    boolean isPlayerAnOwner(String playerId) {
        return ownerId.equals(playerId);
    }

    boolean areAllFleetsSet() {
        return players.values().stream()
                .allMatch(Player::getFleetSet);
    }

    boolean isPlayerFleetSet(String playerId) {
        return players.get(playerId)
                .getFleetSet();
    }

    void markPlayerFleetSet(String playerId) {
        players.get(playerId).setFleetSet(true);
    }

    boolean areAllPlayersReady() {
        return players.values().stream()
                .allMatch(Player::getReady);
    }

    void changePlayerReadiness(String playerId) {
        players.get(playerId).setReady(!players.get(playerId).getReady());
    }

}
