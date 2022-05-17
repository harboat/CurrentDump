package com.github.harboat.core;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @PostMapping
    ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }

}
