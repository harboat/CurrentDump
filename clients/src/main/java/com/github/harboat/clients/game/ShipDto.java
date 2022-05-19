package com.github.harboat.clients.game;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
@Builder
public class ShipDto {
    private ShipType type;
    private Masts masts;
    private OccupiedCells cells;
}
