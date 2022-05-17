package com.github.harboat.core.stats;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document
@NoArgsConstructor
@Getter
@Setter
class PlayerStats {

    @Id
    private String id;

    @Indexed(unique = true)
    @NonNull
    @DocumentReference
    private String playerId;

    @NonNull
    private String playerName;

    @NonNull
    private Integer winnings;

    @NonNull
    private Integer shotsFired;

    @NonNull
    private Integer hits;

    PlayerStats(String playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
    }

    PlayerStats(String playerId, String playerName, Integer winnings, Integer shotsFired, Integer hits) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.winnings = winnings;
        this.shotsFired = shotsFired;
        this.hits = hits;
    }

    void incrementWinnings() {
        winnings++;
    }

    public void incrementShots(int shot) {
        shotsFired++;
        if(shot > 1) hits++;
    }

    @Override
    public String toString() {
        return String.format("%s - winnings: %d; shots fired: %d; accuracy: %.2f%%",
                this.playerName, this.winnings, this.shotsFired, (float) this.hits/this.shotsFired);
    }
}
