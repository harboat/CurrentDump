package com.github.harboat.clients.game;

import java.util.Collection;

public record GameCreated(String gameId, Collection<String> players, String playerTurn) {
}
