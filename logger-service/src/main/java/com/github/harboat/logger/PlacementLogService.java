package com.github.harboat.logger;

//import com.github.harboat.clients.logger.InfoLog;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlacementLogService {

    private final PlacementLogRepository placementLogRepository;

//    public void log(InfoLog<?> infoLog) {
//        System.out.println(infoLog);
//        placementLogRepository.save(
//                PlacementLog.builder()
//                        .serviceId(infoLog.getService())
//                        .message(infoLog.getMessage())
//                        .data(infoLog.getContent())
//                        .createdAt(infoLog.getCreatedAt())
//                        .build()
//        );
//    }
}
