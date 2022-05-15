package com.github.harboat.core.games;

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
@RequestMapping("/api/v1/games")
@Validated
@AllArgsConstructor
public class GameController {

    private final GameService service;

    @PostMapping
    public ResponseEntity<?> create(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        service.create(userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @PostMapping("{gameId}/join")
    public ResponseEntity<?> join(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String gameId
    ) {
        service.join(userDetails.getUsername(), gameId);
        return ResponseEntity.ok().build();
    }

}
