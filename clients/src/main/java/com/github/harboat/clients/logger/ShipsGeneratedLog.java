package com.github.harboat.clients.logger;

import com.github.harboat.clients.core.placement.ShipDto;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Collection;

@AllArgsConstructor
@Builder
public class ShipsGeneratedLog extends PlacementLog {
    private String gameId;
    private String playerId;
    private Collection<ShipDto> ships;
}
