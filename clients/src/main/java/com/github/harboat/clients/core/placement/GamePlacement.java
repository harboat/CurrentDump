package com.github.harboat.clients.core.placement;

import java.util.Collection;

public record GamePlacement(String gameId, String playerId, Collection<ShipDto> ships) {
}
