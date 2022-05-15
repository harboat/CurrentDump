package com.github.harboat.battleships.fleet;

import com.github.harboat.clients.core.placement.OccupiedCells;
import com.github.harboat.clients.core.placement.ShipType;
import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
@Builder
public class Ship {
    private ShipType shipType;
    private MastsState masts;
    private OccupiedCells cells;

    ShipDto toDto() {
        return ShipDto.builder()
                .masts(masts.getMasts().keySet())
                .cells(cells.getPositions())
                .build();
    }
}
