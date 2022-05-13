package com.github.harboat.clients.battleships;

import com.github.harboat.clients.board.Size;

public record GameCreationRequest(String playerId, Size size) {
}
