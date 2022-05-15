package com.github.harboat.clients.core.placement;

import com.github.harboat.clients.core.board.Size;

public record PlacementRequest(String gameId, String playerId, Size size) {
}
