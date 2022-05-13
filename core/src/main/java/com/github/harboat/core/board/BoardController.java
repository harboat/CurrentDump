package com.github.harboat.core.board;

import com.github.harboat.clients.board.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/boards")
@AllArgsConstructor
@Validated
public class BoardController {

    private BoardService service;

    @PostMapping
    ResponseEntity<?> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody Size size
    ) {
        service.create(userDetails.getUsername(), size);
        return ResponseEntity.ok().build();
    }

}
