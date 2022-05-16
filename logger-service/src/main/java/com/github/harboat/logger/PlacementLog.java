package com.github.harboat.logger;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Document(
        collection = "placement-service"
)
@Getter @Setter @ToString
public class PlacementLog<T> extends Log<T> {
    public PlacementLog(String id, ServiceType serviceId, Timestamp createdAt, String message, T data) {
        super(id, serviceId, createdAt, message, data);
    }

    public static <U> PlacementLogBuilder<U> builder() {
        return new PlacementLogBuilder<>();
    }

    public static final class PlacementLogBuilder<U> {
        private String id;
        private ServiceType serviceId;
        private Timestamp createdAt;
        private String message;
        private U data;

        private PlacementLogBuilder() {
        }

        public PlacementLogBuilder<U> id(String id) {
            this.id = id;
            return this;
        }

        public PlacementLogBuilder<U> serviceId(ServiceType serviceId) {
            this.serviceId = serviceId;
            return this;
        }

        public PlacementLogBuilder<U> createdAt(Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PlacementLogBuilder<U> message(String message) {
            this.message = message;
            return this;
        }

        public PlacementLogBuilder<U> data(U data) {
            this.data = data;
            return this;
        }

        public PlacementLog<U> build() {
            return new PlacementLog<>(id, serviceId, createdAt, message, data);
        }
    }
}
