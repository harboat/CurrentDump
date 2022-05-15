package com.github.harboat.battleships.fleet;

import com.github.harboat.battleships.NotificationProducer;
import com.github.harboat.battleships.board.BoardService;
import com.github.harboat.clients.core.placement.GamePlacement;
import com.github.harboat.clients.core.placement.Masts;
import com.github.harboat.clients.notification.EventType;
import com.github.harboat.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FleetService {

    private FleetRepository repository;
    private NotificationProducer producer;
    private BoardService boardService;

    @Transactional
    public void create(GamePlacement placement) {
        List<Ship> ships = getShips(placement);
        Fleet fleet = buildFleet(placement.playerId(), placement, ships);
        repository.save(fleet);
        boardService.markOccupied(placement.gameId(), placement.playerId(), fleet.getAllCells());
        NotificationRequest<FleetDto> notificationRequest = new NotificationRequest<>(
                placement.playerId(), EventType.FLEET_CREATED, fleet.toDto()
        );
        producer.sendNotification(notificationRequest);
    }

    private Fleet buildFleet(String playerId, GamePlacement placement, List<Ship> ships) {
        return Fleet.builder()
                .gameId(placement.gameId())
                .playerId(playerId)
                .ships(ships)
                .build();
    }

    private List<Ship> getShips(GamePlacement response) {
        return response.ships()
                .stream()
                .map(s -> Ship.builder()
                        .shipType(s.getType())
                        .masts(convertMasts(s.getMasts()))
                        .cells(s.getCells())
                        .build()
                ).toList();
    }

    public MastsState convertMasts(Masts masts) {
        return new MastsState(
                masts.getPositions()
                        .stream()
                        .collect(Collectors.toMap(m -> m, m -> MastState.ALIVE))
        );
    }

}
