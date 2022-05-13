package com.github.harboat.core;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/placement")
@AllArgsConstructor
@Validated
public class PlacementController {

    private PlacementQueueProducer producer;

    @PostMapping
    public ResponseEntity<?> randomPlacement() {
        return null;
    }

}
