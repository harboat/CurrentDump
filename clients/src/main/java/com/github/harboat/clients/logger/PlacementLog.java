package com.github.harboat.clients.logger;

public abstract class PlacementLog extends Log {
    public PlacementLog(LogType type) {
        super(ServiceType.PLACEMENT, type);
    }
}
