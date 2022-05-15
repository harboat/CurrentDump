package com.github.harboat.clients.logger;

public abstract class GameLog extends Log {
    public GameLog() {
        super(ServiceType.GAME);
    }
}

