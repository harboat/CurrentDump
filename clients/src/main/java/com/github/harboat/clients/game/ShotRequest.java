package com.github.harboat.clients.game;

public record ShotRequest(String gameId, String playerId, Integer cellId) {
}
