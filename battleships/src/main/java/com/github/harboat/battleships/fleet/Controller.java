package com.github.harboat.battleships.fleet;

import com.github.harboat.clients.board.Size;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class Controller {

    FleetService service;

    @PostMapping("/")
    void send(@AuthenticationPrincipal UserDetails details) {
        service.create("123214", details.getUsername(), new Size(10, 10));
    }

}
