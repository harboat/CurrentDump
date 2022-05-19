package com.github.harboat.clients.game;

import java.util.Collection;

public record ShotResponse(String gameId, String playerId, Collection<Cell> cells) {
}
