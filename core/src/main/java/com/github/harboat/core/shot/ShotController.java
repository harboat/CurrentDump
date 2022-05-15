package com.github.harboat.core.shot;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/games/{gameId}/shoot")
@AllArgsConstructor
public class ShotController {

    private final ShootService service;

    @PostMapping
    public ResponseEntity<?> shot(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String gameId,
            @Valid @RequestBody ShotDto shotDto
    ) {
        service.takeAShoot(gameId, userDetails.getUsername(), shotDto.cellId());
        return ResponseEntity.accepted().build();
    }

}
