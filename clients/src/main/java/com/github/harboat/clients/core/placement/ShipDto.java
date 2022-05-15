package com.github.harboat.clients.core.placement;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
@Builder
public class ShipDto {
    private ShipType type;
    private Masts masts;
    private OccupiedCells cells;
}
