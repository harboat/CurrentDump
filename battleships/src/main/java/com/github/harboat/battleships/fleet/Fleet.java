package com.github.harboat.battleships.fleet;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
@Builder
public class Fleet {
    @Id private String id;
    private String gameId;
    private String playerId;
    private Collection<Ship> ships;

    FleetDto toDto() {
        return FleetDto.builder()
                .gameId(gameId)
                .playerId(playerId)
                .ships(ships.stream().map(Ship::toDto).toList())
                .build();
    }
}
