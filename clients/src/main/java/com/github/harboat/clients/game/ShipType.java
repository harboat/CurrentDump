package com.github.harboat.clients.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ShipType {
    DESTROYER(1),
    SUBMARINE(2),
    CRUISER(3),
    BATTLESHIP(4);
    private final int size;
}
