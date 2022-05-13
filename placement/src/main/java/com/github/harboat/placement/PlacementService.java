package com.github.harboat.placement;

import com.github.harboat.clients.placement.PlacementRequest;
import com.github.harboat.clients.placement.PlacementResponse;
import com.github.harboat.clients.placement.ShipDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class PlacementService {

    private final PlacementRepository repository;
    private final PlacementSender sender;

    void generate(PlacementRequest placementRequest) {
        RandomShipsPlacementGenerator generator = new RandomShipsPlacementGenerator(placementRequest.size());
        Collection<ShipDto> ships = generator.generateRandomlyPlacedFleet();
        Placement placement = Placement.builder()
                .gameId(placementRequest.gameId())
                .playerId(placementRequest.playerId())
                .ships(ships)
                .build();
        PlacementResponse response = new PlacementResponse(placementRequest.gameId(), placementRequest.playerId(), ships);
        repository.save(placement);
        sender.sendResponse(response);
    }

}
