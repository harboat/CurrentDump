package com.github.harboat.clients.logger;

import java.sql.Timestamp;

public abstract class GameLog extends Log {
    public GameLog(LogType type) {
        super(ServiceType.GAME, type);
    }
}

