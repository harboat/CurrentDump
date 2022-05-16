package com.github.harboat.logger;

import com.github.harboat.clients.logger.ServiceType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Document(
        collection = "main-service"
)
@Getter @Setter @ToString
public class CoreLog<T> extends Log<T> {
    public CoreLog(String id, ServiceType serviceId, Timestamp createdAt, String message, T data) {
        super(id, serviceId, createdAt, message, data);
    }

    public static <U> CoreLogBuilder<U> builder() {
        return new CoreLogBuilder<>();
    }

    public static final class CoreLogBuilder<U> {
        private String id;
        private ServiceType serviceId;
        private Timestamp createdAt;
        private String message;
        private U data;

        private CoreLogBuilder() {
        }

        public CoreLogBuilder<U> id(String id) {
            this.id = id;
            return this;
        }

        public CoreLogBuilder<U> serviceId(ServiceType serviceId) {
            this.serviceId = serviceId;
            return this;
        }

        public CoreLogBuilder<U> createdAt(Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CoreLogBuilder<U> message(String message) {
            this.message = message;
            return this;
        }

        public CoreLogBuilder<U> data(U data) {
            this.data = data;
            return this;
        }

        public CoreLog<U> build() {
            return new CoreLog<>(id, serviceId, createdAt, message, data);
        }
    }
}
