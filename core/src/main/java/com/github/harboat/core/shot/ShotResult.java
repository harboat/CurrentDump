package com.github.harboat.core.shot;

import com.github.harboat.clients.core.shot.Cell;

import java.util.Collection;

public record ShotResult(String playerId, Collection<Cell> cells) {
}
