package com.github.harboat.clients.logger;

public abstract class PlacementLog extends Log {
    public PlacementLog() {
        super(ServiceType.PLACEMENT);
    }
}
