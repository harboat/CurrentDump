package com.github.harboat.core.room;

import lombok.Getter;

/**
 * @author Herman Ciechanowiec
 */

@Getter
class Room {

    private final int roomNumber;
    private String firstUsername;
    private String secondUsername;

    Room(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    void addPlayerToRoom(String username) {
        if (firstUsername == null) {
            firstUsername = username;
        } else if (secondUsername == null) {
            secondUsername = username;
        } else {
            throw new RoomFullyOccupiedException(String.valueOf(roomNumber));
        }
    }

    void deletePlayerFromRoom(String username) {
        if (firstUsername != null && firstUsername.equals(username)) {
            firstUsername = null;
        } else if (secondUsername != null && secondUsername.equals(username)) {
            secondUsername = null;
        } else {
            throw new NoSpecifiedUserInRoomException(roomNumber, username);
        }
    }
}
