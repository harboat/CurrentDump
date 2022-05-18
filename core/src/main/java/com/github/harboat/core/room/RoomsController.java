package com.github.harboat.core.room;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Herman Ciechanowiec
 */
@RestController
@RequestMapping("/api/v1/rooms")
@AllArgsConstructor
class RoomsController {

    //TODO: remove the player from the room if the player has left the room page
    private final RoomsService roomsService;

    @GetMapping("/all")
    ResponseEntity<RoomsCollection> getAllRooms() {
        RoomsCollection roomsCollection = roomsService.getRoomsCollection();
        return ResponseEntity.ok(roomsCollection);
    }

    @GetMapping("/{roomNumber}")
    ResponseEntity<Room> getRoom(@PathVariable(name = "roomNumber")
                                 String roomNumber) {
        Room requestedRoom = roomsService.getRoom(roomNumber);
        return ResponseEntity.ok(requestedRoom);
    }

    // TODO: get username from @AuthenticationPrincipal UserDetails userDetails
    @PutMapping("/{roomNumber}")
    ResponseEntity<String> addPlayerToRoom(@PathVariable(name = "roomNumber")
                                           String roomNumber,
                                           @RequestParam
                                           String username) {
        String responseMessage = roomsService.addPlayerToRoom(roomNumber, username);
        return ResponseEntity.ok(responseMessage);
    }

    @DeleteMapping("/{roomNumber}")
    ResponseEntity<String> deletePlayerFromRoom(@PathVariable(name = "roomNumber")
                                                String roomNumber,
                                                @RequestParam
                                                String username) {
        String responseMessage = roomsService.deletePlayerFromRoom(roomNumber, username);
        return ResponseEntity.ok(responseMessage);
    }
}
