package com.github.harboat.clients.logger;

import com.github.harboat.clients.core.placement.ShipDto;
import lombok.*;

import java.util.Collection;

@Getter @Setter
@ToString
public class ShipsGeneratedLog extends PlacementLog {
    private String gameId;
    private String playerId;
    private Collection<ShipDto> ships;

    public ShipsGeneratedLog(LogType type) {
        super(type);
    }

    public static ShipsGeneratedLogBuilder builder() {
        return ShipsGeneratedLogBuilder.builder();
    }

    public static final class ShipsGeneratedLogBuilder {
        private LogType type;
        private String gameId;
        private String playerId;
        private Collection<ShipDto> ships;

        private ShipsGeneratedLogBuilder() {
        }

        public static ShipsGeneratedLogBuilder builder() {
            return new ShipsGeneratedLogBuilder();
        }

        public ShipsGeneratedLogBuilder type(LogType type) {
            this.type = type;
            return this;
        }

        public ShipsGeneratedLogBuilder gameId(String gameId) {
            this.gameId = gameId;
            return this;
        }

        public ShipsGeneratedLogBuilder playerId(String playerId) {
            this.playerId = playerId;
            return this;
        }

        public ShipsGeneratedLogBuilder ships(Collection<ShipDto> ships) {
            this.ships = ships;
            return this;
        }

        public ShipsGeneratedLog build() {
            ShipsGeneratedLog shipsGeneratedLog = new ShipsGeneratedLog(type);
            shipsGeneratedLog.setGameId(gameId);
            shipsGeneratedLog.setPlayerId(playerId);
            shipsGeneratedLog.setShips(ships);
            return shipsGeneratedLog;
        }
    }
}
