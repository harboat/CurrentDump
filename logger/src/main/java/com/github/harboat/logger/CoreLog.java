package com.github.harboat.logger;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Document(
        collection = "main-service"
)
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
@Builder
public class CoreLog<T> extends Log<T> {
}
