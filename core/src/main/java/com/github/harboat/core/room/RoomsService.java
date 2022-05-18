package com.github.harboat.core.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

/**
 * @author Herman Ciechanowiec
 */
@Service
@AllArgsConstructor
@Getter
class RoomsService {

    private final RoomsCollection roomsCollection;

    String addPlayerToRoom(String roomNumber, String username) {
        roomsCollection.addPlayerToRoom(roomNumber, username);
        return String.format("The user '%s' has been added to the room #'%s'",
                             username, roomNumber);
    }

    String deletePlayerFromRoom(String roomNumber, String username) {
        roomsCollection.deletePlayerFromRoom(roomNumber, username);
        return String.format("The user '%s' has been removed from the room #'%s'",
                             username, roomNumber);
    }

    Room getRoom(String roomNumber) {
        return roomsCollection.getRoom(roomNumber);
    }
}
