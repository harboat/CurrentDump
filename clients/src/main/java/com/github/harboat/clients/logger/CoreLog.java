package com.github.harboat.clients.logger;

public abstract class CoreLog extends Log {
    public CoreLog() {
        super(ServiceType.CORE);
    }
}
