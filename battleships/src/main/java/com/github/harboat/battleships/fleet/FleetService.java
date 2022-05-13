package com.github.harboat.battleships.fleet;

import com.github.harboat.battleships.websocket.Event;
import com.github.harboat.battleships.websocket.EventType;
import com.github.harboat.battleships.websocket.WebsocketService;
import com.github.harboat.clients.board.Size;
import com.github.harboat.clients.placement.Masts;
import com.github.harboat.clients.placement.PlacementRequest;
import com.github.harboat.clients.placement.PlacementResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FleetService {

    private FleetRepository repository;
    private PlacementRequestSender sender;
    private WebsocketService websocketService;

    public void create(String gameId, String playerId, Size size) {
        sender.sendRequest(
                new PlacementRequest(gameId, playerId, size)
        );
    }

    public void create(PlacementResponse response) {
        List<Ship> ships = getShips(response);
        Fleet fleet = buildFleet(response, ships);
        Event<FleetDto> fleetDto = new Event<>(
            EventType.FLEET_CREATED, fleet.toDto()
        );
        repository.save(fleet);
        websocketService.notifyFrontEnd(response.playerId(), fleetDto);
    }

    private Fleet buildFleet(PlacementResponse response, List<Ship> ships) {
        return Fleet.builder()
                .gameId(response.gameId())
                .playerId(response.playerId())
                .ships(ships)
                .build();
    }

    private List<Ship> getShips(PlacementResponse response) {
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
