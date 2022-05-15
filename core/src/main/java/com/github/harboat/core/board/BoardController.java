package com.github.harboat.core.board;

import com.github.harboat.clients.core.board.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/games/{gameId}/boards")
@Validated
@AllArgsConstructor
public class BoardController {

    private BoardService service;

    @PostMapping
    ResponseEntity<?> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String gameId,
            // todo: create dto
            @Valid @RequestBody Size size
    ) {
        service.create(gameId, userDetails.getUsername(), size);
        return ResponseEntity.ok().build();
    }

}
