package com.github.harboat.placement;

import com.github.harboat.clients.configuration.SetShipsPosition;
import com.github.harboat.clients.game.ShipDto;
import com.github.harboat.clients.placement.GeneratePlacement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class PlacementService {

    private final PlacementRepository repository;
    private final ConfigQueueProducer sender;

    void generate(GeneratePlacement generatePlacement) {
        RandomShipsPlacementGenerator generator = new RandomShipsPlacementGenerator(generatePlacement.size());
        Collection<ShipDto> ships = generator.generateRandomlyPlacedFleet();
        Placement placement = Placement.builder()
                .roomId(generatePlacement.roomId())
                .playerId(generatePlacement.playerId())
                .ships(ships)
                .build();
        repository.save(placement);
        sender.sendPlacement(
                new SetShipsPosition(
                        generatePlacement.roomId(), generatePlacement.playerId(), ships
                )
        );
    }

}
