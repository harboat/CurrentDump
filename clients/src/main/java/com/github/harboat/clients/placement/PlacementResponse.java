package com.github.harboat.clients.placement;

import java.util.Collection;

public record PlacementResponse(String gameId, String playerId, Collection<ShipDto> ships) {
}
