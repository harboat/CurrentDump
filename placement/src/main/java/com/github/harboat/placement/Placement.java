package com.github.harboat.placement;

import com.github.harboat.clients.placement.ShipDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
@Builder
public class Placement {
    @Id private String id;
    private String gameId;
    private String playerId;
    private Collection<ShipDto> ships;
}
