package com.github.harboat.battleships.game;

import com.github.harboat.battleships.board.Board;
import com.github.harboat.battleships.fleet.Fleet;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Collection;

@Document
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
@Builder
public class Game {
    @Id private String id;

    private Collection<String> playerIds;

//    @DocumentReference(lookup = "{'gameId':?#{#self._id} }")
//    private Collection<Board> boards;
//
//    @DocumentReference(lookup = "{'gameId':?#{#self._id} }")
//    private Collection<Fleet> fleets;

    private String turnOfPlayer;
}
