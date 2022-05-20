package com.github.harboat.clients.configuration;

import com.github.harboat.clients.game.Size;

public record SetGameSize(String roomId, String playerId, Size size) {
}
