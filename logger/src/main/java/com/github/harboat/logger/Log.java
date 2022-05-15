package com.github.harboat.logger;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Getter @Setter
@Builder
abstract class Log<T> {
    @Id
    protected String id;
    protected String serviceId;
    protected Timestamp createdAt;
    protected String message;
    protected T data;
}
