package com.github.harboat.clients.core;

import com.github.harboat.clients.board.Size;

public record GameCreationResponse(String gameId, String playerId, Size size) {
}
