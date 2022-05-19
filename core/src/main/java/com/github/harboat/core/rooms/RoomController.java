package com.github.harboat.core.rooms;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/v1/rooms")
@AllArgsConstructor
public class RoomController {

    private RoomService roomService;

    @PostMapping
    public ResponseEntity<?> create(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        roomService.create(userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @PostMapping("{roomId}/ready")
    public ResponseEntity<?> changeReadiness(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String roomId
    ) {
        roomService.changeReadiness(roomId, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @PostMapping("{roomId}/start")
    public ResponseEntity<?> markStart(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String roomId
    ) {
        roomService.start(roomId, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }
}
