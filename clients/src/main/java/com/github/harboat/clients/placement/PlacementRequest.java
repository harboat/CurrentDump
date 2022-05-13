package com.github.harboat.clients.placement;

import com.github.harboat.clients.board.Size;

public record PlacementRequest(String gameId, String playerId, Size size) {
}
