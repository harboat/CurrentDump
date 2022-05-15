package com.github.harboat.logger;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(
        collection = "placement-service"
)
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
@Builder
public class GameLog<T> extends Log<T> {
}
