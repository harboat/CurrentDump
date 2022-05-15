package com.github.harboat.clients.core.game;

import com.github.harboat.clients.core.board.Size;

public record PlayerJoinedResponse(String gameId, String playerId, String playerTurn) {
}
