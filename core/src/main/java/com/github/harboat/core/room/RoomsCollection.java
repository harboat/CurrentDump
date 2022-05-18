package com.github.harboat.core.room;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author Herman Ciechanowiec
 */
@Component
@Getter
class RoomsCollection {

    private static final int roomsNumber = 4;
    private final Map<Integer, Room> rooms;

    RoomsCollection() {
        rooms = produceInitRooms();
    }

    private Map<Integer, Room> produceInitRooms() {
        Map<Integer, Room> rooms = new HashMap<>();
        IntStream.rangeClosed(1, roomsNumber)
                 .forEach(roomNumber ->
                          rooms.put(roomNumber, new Room(roomNumber)));
        return rooms;
    }

    void addPlayerToRoom(String roomNumber, String username) {
        rejectInvalidInt(roomNumber);
        rejectNotExistingRoom(roomNumber);
        int roomNumberAsInt = Integer.parseInt(roomNumber);
        Room requestedRoom = rooms.get(roomNumberAsInt);
        requestedRoom.addPlayerToRoom(username);
    }

    void deletePlayerFromRoom(String roomNumber, String username) {
        rejectInvalidInt(roomNumber);
        rejectNotExistingRoom(roomNumber);
        int roomNumberAsInt = Integer.parseInt(roomNumber);
        Room requestedRoom = rooms.get(roomNumberAsInt);
        requestedRoom.deletePlayerFromRoom(username);
    }

    private void rejectNotExistingRoom(String providedRoomNumber) {
        if (!isValidInt(providedRoomNumber)
            || !rooms.containsKey(Integer.parseInt(providedRoomNumber))) {
            throw new RoomNotFoundException(providedRoomNumber);
        }
    }

    private void rejectInvalidInt(String providedNumber) {
        try {
            Integer.parseInt(providedNumber);
        } catch (NumberFormatException exception) {
            throw new RoomNumberFormatException(providedNumber);
        }
    }

    private boolean isValidInt(String providedNumber) {
        try {
            Integer.parseInt(providedNumber);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    Room getRoom(String roomNumber) {
        rejectInvalidInt(roomNumber);
        rejectNotExistingRoom(roomNumber);
        int roomNumberAsInt = Integer.parseInt(roomNumber);
        return rooms.get(roomNumberAsInt);
    }
}
