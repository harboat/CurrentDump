package com.github.harboat.logger;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter @Setter
abstract class Log {
    @Id
    protected String id;
}
