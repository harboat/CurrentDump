package com.github.harboat.battleships.shot;

import com.github.harboat.battleships.NotificationProducer;
import com.github.harboat.battleships.fleet.FleetService;
import com.github.harboat.clients.core.shot.ShotRequest;
import com.github.harboat.clients.notification.EventType;
import com.github.harboat.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShotService {

    private ShotRepository repository;
    private FleetService fleetService;
    private NotificationProducer notificationProducer;

    public void takeAShoot(ShotRequest shotRequest) {
        var shotResult = fleetService.shoot(shotRequest);
        var shot = Shot.builder()
                .gameId(shotRequest.gameId())
                .playerId(shotRequest.username())
                .cellId(shotRequest.cellId())
                .shotResult(shotResult)
                .build();
        repository.save(shot);
        notificationProducer.sendNotification(new NotificationRequest<>(shotRequest.username(), EventType.HIT, shot));
    }
}
