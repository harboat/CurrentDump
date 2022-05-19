package com.github.harboat.clients.configuration;

import com.github.harboat.clients.core.board.Size;

public record SetGameSize(String roomId, String playerId, Size size) {
}
