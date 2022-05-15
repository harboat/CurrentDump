package com.github.harboat.logger;

import com.github.harboat.clients.logger.GenericLog;
import com.github.harboat.clients.logger.ShipsGeneratedLog;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlacementLogService {

    private final PlacementLogRepository placementLogRepository;


    public void log(ShipsGeneratedLog generatedLog) {

    }

    public void log(GenericLog genericLog) {
        placementLogRepository.save(
                PlacementLog.builder()
                        .build()
        );
    }
}
