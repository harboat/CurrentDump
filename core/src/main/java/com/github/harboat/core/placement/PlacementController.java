package com.github.harboat.core.placement;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/placements")
@AllArgsConstructor
@Validated
public class PlacementController {

    private PlacementService service;

    @PostMapping("{gameId}")
    public ResponseEntity<?> randomPlacement(
            @AuthenticationPrincipal UserDetails details,
            @PathVariable String gameId
    ) {
        service.palaceShips(gameId, details.getUsername());
        return ResponseEntity.ok().build();
    }

}
