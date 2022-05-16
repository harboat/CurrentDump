package com.github.harboat.logger;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class InfoLog<T> extends Log {
    private String className;
    private String methodName;
    private String message;
    private T content;

    public InfoLog(ServiceType service) {
        super(service, LogType.INFO);
    }

    public InfoLog() {}

    public static <K> InfoLogBuilder<K> builder() {
        return InfoLogBuilder.builder();
    }

    public static final class InfoLogBuilder<K> {
        private ServiceType service;
        private String message;
        private String className;
        private String methodName;
        private K content;

        private InfoLogBuilder() {
        }

        public static <U> InfoLogBuilder<U> builder() {
            return new InfoLogBuilder<>();
        }

        public InfoLogBuilder<K> className(String className) {
            this.className = className;
            return this;
        }

        public InfoLogBuilder<K> methodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public InfoLogBuilder<K> message(String message) {
            this.message = message;
            return this;
        }

        public InfoLogBuilder<K> content(K content) {
            this.content = content;
            return this;
        }

        public InfoLogBuilder<K> service(ServiceType service) {
            this.service = service;
            return this;
        }

        public InfoLog<K> build() {
            InfoLog<K> infoLog = new InfoLog<>(service);
            infoLog.setClassName(className);
            infoLog.setMethodName(methodName);
            infoLog.setMessage(message);
            infoLog.setContent(content);
            return infoLog;
        }
    }
}
