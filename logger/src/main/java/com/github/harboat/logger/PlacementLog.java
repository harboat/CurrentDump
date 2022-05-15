package com.github.harboat.logger;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Document(
        collection = "placement-service"
)
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
public class PlacementLog<T> extends Log<T> {
}
