package com.github.harboat.logger;

import com.github.harboat.clients.logger.ServiceType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Document(
        collection = "placement-service"
)
@Getter @Setter @ToString
public class GameLog<T> extends Log<T> {
    public GameLog(String id, ServiceType serviceId, Timestamp createdAt, String message, T data) {
        super(id, serviceId, createdAt, message, data);
    }

    public static <U> GameLogBuilder<U> builder() {
        return new GameLogBuilder<>();
    }

    public static final class GameLogBuilder<U> {
        private String id;
        private ServiceType serviceId;
        private Timestamp createdAt;
        private String message;
        private U data;

        private GameLogBuilder() {
        }

        public GameLogBuilder<U> id(String id) {
            this.id = id;
            return this;
        }

        public GameLogBuilder<U> serviceId(ServiceType serviceId) {
            this.serviceId = serviceId;
            return this;
        }

        public GameLogBuilder<U> createdAt(Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public GameLogBuilder<U> message(String message) {
            this.message = message;
            return this;
        }

        public GameLogBuilder<U> data(U data) {
            this.data = data;
            return this;
        }

        public GameLog<U> build() {
            return new GameLog<>(id, serviceId, createdAt, message, data);
        }
    }
}
