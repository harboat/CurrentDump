package com.github.harboat.clients.game;

import lombok.*;

import java.util.Collection;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
public class OccupiedCells {
    private Collection<Integer> positions;
}
