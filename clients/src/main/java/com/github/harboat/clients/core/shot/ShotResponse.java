package com.github.harboat.clients.core.shot;

import java.util.Collection;

public record ShotResponse(String gameId, String playerId, Collection<Cell> cells) {
}
