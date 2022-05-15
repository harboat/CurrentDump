package com.github.harboat.placement;

import com.github.harboat.clients.core.placement.GamePlacement;
import com.github.harboat.clients.core.placement.PlacementRequest;
import com.github.harboat.clients.core.placement.ShipDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class PlacementService {

    private final PlacementRepository repository;
    private final PlacementProducer sender;

    void generate(PlacementRequest placementRequest) {
        RandomShipsPlacementGenerator generator = new RandomShipsPlacementGenerator(placementRequest.size());
        Collection<ShipDto> ships = generator.generateRandomlyPlacedFleet();
        Placement placement = Placement.builder()
                .gameId(placementRequest.gameId())
                .playerId(placementRequest.playerId())
                .ships(ships)
                .build();
        repository.save(placement);
        GamePlacement gamePlacement = new GamePlacement(placementRequest.gameId(), placementRequest.playerId(), ships);
        sender.sendPlacement(gamePlacement);
    }

}
