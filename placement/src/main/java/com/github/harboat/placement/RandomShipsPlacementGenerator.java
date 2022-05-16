package com.github.harboat.placement;

import com.github.harboat.clients.core.board.Size;
import com.github.harboat.clients.core.placement.Masts;
import com.github.harboat.clients.core.placement.ShipDto;
import com.github.harboat.clients.core.placement.ShipType;

import java.security.SecureRandom;
import java.util.*;

public class RandomShipsPlacementGenerator {

    private final Size size;
    private final List<ShipType> ships;
    private final Set<Integer> occupiedCells;

    RandomShipsPlacementGenerator(Size size) {
        this.size = size;
        occupiedCells = new HashSet<>();
        ships = new ArrayList<>(List.of(
                ShipType.DESTROYER,
                ShipType.DESTROYER,
                ShipType.DESTROYER,
                ShipType.DESTROYER,
                ShipType.SUBMARINE,
                ShipType.SUBMARINE,
                ShipType.SUBMARINE,
                ShipType.CRUISER,
                ShipType.CRUISER,
                ShipType.BATTLESHIP
        ));
    }

    /**
     * Provides randomly generated ship positions.
     *
     * @return {@link java.util.List}
     */
    Collection<ShipDto> generateRandomlyPlacedFleet() {
        Collection<ShipDto> ships = new ArrayList<>();
        while (this.ships.size() > 0) {
            int randomShipSizeIndex = new SecureRandom().nextInt(this.ships.size());
            ships.add(generateShip(this.ships.get(randomShipSizeIndex)));
            this.ships.remove(randomShipSizeIndex);
        }
        return ships;
    }

    private ShipDto generateShip(ShipType shipType) {
        List<List<Integer>> allLegalShipPositions = new PossibleShipPositionsGenerator(size)
                .generateShipPositions(shipType.getSize(), occupiedCells);
        List<Integer> randomShipPosition = allLegalShipPositions
                .get(new SecureRandom().nextInt(0, allLegalShipPositions.size()));
        Masts masts = new Masts(randomShipPosition);
        ShipCreator shipCreator = new ShipCreator(size, shipType, masts);
        ShipDto shipDto = shipCreator.create();
        occupiedCells.addAll(shipDto.getCells().getPositions());
        occupiedCells.addAll(shipDto.getMasts().getPositions());
        return shipCreator.create();
    }
}

