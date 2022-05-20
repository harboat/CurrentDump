package com.github.harboat.core.rooms;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoomUtility {
    private RoomRepository repository;

    public Optional<Room> findRoomById(String roomId) {
        return repository.findByRoomId(roomId);
    }

}
