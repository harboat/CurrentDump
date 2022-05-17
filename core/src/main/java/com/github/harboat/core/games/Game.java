package com.github.harboat.core.games;

import com.github.harboat.clients.core.board.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
@Builder
public class Game {
    @Id private String id;
    private String gameId;
    private Collection<String> players;
    private String ownerId;
    private String playerTurn;
    private Size size;
    private Collection<Boolean> feelWasSet;
    private Boolean started;
    private Boolean ended;
}
