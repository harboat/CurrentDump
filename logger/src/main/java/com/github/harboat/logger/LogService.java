package com.github.harboat.logger;

import com.github.harboat.clients.logger.MainServiceLog;
import com.github.harboat.clients.logger.PlacementServiceLog;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class LogService {

    private final PlacementLogRepository placementLogRepository;
    private final MainLogRepository mainLogRepository;

    public void log(PlacementServiceLog toBeLogged) {
        placementLogRepository.save(
                PlacementServiceLogDocument.builder()
                        .serviceId(toBeLogged.serviceId())
                        .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                        .build()
        );
    }

    public void log(MainServiceLog toBeLogged) {
        mainLogRepository.save(
            MainServiceLogDocument.builder()
                    .serviceId(toBeLogged.serviceId())
                    .createdAt(toBeLogged.createdAt())
                    .build()
        );
    }
}
