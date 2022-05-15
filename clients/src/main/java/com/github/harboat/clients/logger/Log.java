package com.github.harboat.clients.logger;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public abstract class Log {
    protected LogType type;
    protected final ServiceType service;
    protected Timestamp createdAt;

    public Log(ServiceType service) {
        this.service = service;
        createdAt = Timestamp.valueOf(LocalDateTime.now());
    }
}
