package com.github.harboat.clients.core.game;

public record PlayerJoinedResponse(String gameId, String playerId, String playerTurn) {
}
