package com.github.harboat.logger;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Getter @Setter
abstract class Log<T> {
    @Id
    protected String id;
    protected ServiceType serviceType;
    protected Timestamp createdAt;
    protected String message;
    protected T data;

    public Log(String id, ServiceType serviceType, Timestamp createdAt, String message, T data) {
        this.id = id;
        this.serviceType = serviceType;
        this.createdAt = createdAt;
        this.message = message;
        this.data = data;
    }
}
