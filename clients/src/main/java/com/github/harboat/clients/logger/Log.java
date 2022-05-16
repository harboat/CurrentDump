package com.github.harboat.clients.logger;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Setter @Getter
public abstract class Log {
    protected LogType type;
    protected final ServiceType service;
    protected Timestamp createdAt;

    public Log(ServiceType service, LogType logType) {
        this.type = logType;
        this.service = service;
        createdAt = Timestamp.valueOf(LocalDateTime.now());
    }
}
