package com.github.harboat.clients.configuration;

import com.github.harboat.clients.game.ShipDto;

import java.util.Collection;

public record SetShipsPosition(String roomId, String playerId, Collection<ShipDto> ships) {
}
