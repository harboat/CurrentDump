package com.github.harboat.core.placement;

import com.github.harboat.clients.board.Size;
import com.github.harboat.clients.placement.PlacementRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/placement")
@AllArgsConstructor
@Validated
public class PlacementController {

    private PlacementService service;

    @PostMapping("{gameId}")
    public ResponseEntity<?> randomPlacement(
            @AuthenticationPrincipal UserDetails details,
            @PathVariable String gameId,
            // TODO: create DTO
            @Valid @RequestBody Size size
    ) {
        service.palaceShips(gameId, details.getUsername(), size);
        return ResponseEntity.ok().build();
    }

}
