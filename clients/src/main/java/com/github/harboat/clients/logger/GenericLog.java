package com.github.harboat.clients.logger;

import lombok.*;

@Getter @Setter
@ToString
public class GenericLog<T> extends Log {
    private String message;
    private T content;
    public GenericLog(ServiceType service, LogType logType, String message) {
        super(service, logType);
        this.message = message;
    }

    public static <U> GenericLogBuilder<U> builder() {
        return new GenericLogBuilder<>();
    }

    public static final class GenericLogBuilder<U> {
        private LogType type;
        private ServiceType service;
        private String message;
        private U content;

        private GenericLogBuilder() {
        }

        public GenericLogBuilder<U> builder() {
            return new GenericLogBuilder<>();
        }

        public GenericLogBuilder<U> message(String message) {
            this.message = message;
            return this;
        }

        public GenericLogBuilder<U> content(U content) {
            this.content = content;
            return this;
        }

        public GenericLogBuilder<U> type(LogType type) {
            this.type = type;
            return this;
        }

        public GenericLogBuilder<U> service(ServiceType service) {
            this.service = service;
            return this;
        }

        public GenericLog<U> build() {
            GenericLog<U> genericLog = new GenericLog<>(service, type, message);
            genericLog.setContent(content);
            return genericLog;
        }
    }
}
