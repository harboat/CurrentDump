package com.github.harboat.clients.logger;

import lombok.*;

@Getter @Setter
@Builder
public class GenericLog extends Log {
    private String message;
}
