package com.github.harboat.battleships;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "com.github.harboat.rabbitmq",
                "com.github.harboat.battleships"
        }
)
public class BattleshipsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BattleshipsApplication.class, args);
    }

}
