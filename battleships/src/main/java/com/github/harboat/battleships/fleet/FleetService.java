package com.github.harboat.battleships.fleet;

import com.github.harboat.battleships.CoreQueueProducer;
import com.github.harboat.battleships.NotificationProducer;
import com.github.harboat.battleships.board.BoardService;
import com.github.harboat.battleships.game.GameService;
import com.github.harboat.battleships.game.GameUtility;
import com.github.harboat.clients.core.placement.GamePlacement;
import com.github.harboat.clients.core.placement.Masts;
import com.github.harboat.clients.core.placement.PlacementResponse;
import com.github.harboat.clients.core.shot.*;
import com.github.harboat.clients.notification.EventType;
import com.github.harboat.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class FleetService {

    private FleetRepository repository;
    private GameUtility gameUtility;
    private NotificationProducer producer;
    private CoreQueueProducer coreQueueProducer;
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
        coreQueueProducer.sendResponse(
                new PlacementResponse(placement.gameId(), placement.playerId())
        );
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

    public void shoot(ShotRequest shotRequest) {
        var gameId = shotRequest.gameId();
        var playerId = shotRequest.playerId();
        var cellId = shotRequest.cellId();
        var enemyId = gameUtility.getEnemyId(gameId, playerId);
        var currentFleet = repository.findByGameIdAndPlayerId(gameId, enemyId).orElseThrow();
        Set<Cell> cells = takeAShoot(gameId, playerId, cellId, currentFleet);
        repository.save(currentFleet);
        coreQueueProducer.sendResponse(
                new ShotResponse(gameId, playerId, cells)
        );
    }

    public void shoot(NukeShotRequest nukeShotRequest) {
        var gameId = nukeShotRequest.gameId();
        var playerId = nukeShotRequest.playerId();
        var cellId = nukeShotRequest.cellId();
        var enemyId = gameUtility.getEnemyId(gameId, playerId);
        var currentFleet = repository.findByGameIdAndPlayerId(gameId, enemyId).orElseThrow();

        //TODO replace hardcoded board width with variable
        List<Integer> nukedCells = determineNukedCells(cellId, 10);

        Set<Cell> cells = new HashSet<>();
        for (Integer cell : nukedCells) {
            cells.addAll(takeAShoot(gameId, playerId, cell, currentFleet));
        }

        repository.save(currentFleet);
        coreQueueProducer.sendResponse(
                new

                        ShotResponse(gameId, playerId, cells)
        );
    }

    private Set<Cell> takeAShoot(String gameId, String playerId, Integer cellId, Fleet currentFleet) {
        Optional<Ship> ship = currentFleet.takeAShot(cellId);
        boardService.markHit(gameId, playerId, cellId);
        if (ship.isEmpty()) {
            return Set.of(new Cell(cellId, false));
        }
        if (!currentFleet.isAlive()) {
            coreQueueProducer.sendResponse(
                    new PlayerWon(gameId, playerId)
            );
        }
        Ship s = ship.get();
        Set<Cell> cells = new HashSet<>();
        cells.add(new Cell(cellId, true));
        if (!s.isAlive()) {
            s.getCells().getPositions()
                    .forEach(c -> cells.add(new Cell(c, false)));
            s.getMasts().getMasts().keySet()
                    .forEach(m -> cells.add(new Cell(m, true)));
        }
        return cells;
    }

    private List<Integer> determineNukedCells(Integer cellId, int boardWidth) {
        List<Integer> adjacentFields = new ArrayList<>();
        adjacentFields.add(cellId);
        adjacentFields.addAll(addLeftCells(cellId, boardWidth));
        adjacentFields.addAll(addRightCells(cellId, boardWidth));
        adjacentFields.addAll(addTopandBottomCells(cellId, boardWidth));
        return adjacentFields.stream().filter(i -> i > 0 && i < boardWidth*boardWidth).collect(Collectors.toList());
    }

    private List<Integer> addLeftCells(int cellId, int boardWidth) {
        if(cellId % boardWidth == 1) return Collections.emptyList();
        return List.of(cellId + boardWidth - 1, cellId - 1, cellId - boardWidth - 1);
    }

    private List<Integer> addRightCells(int cellId, int boardWidth) {
        if(cellId % boardWidth == 0) return Collections.emptyList();
        return List.of(cellId - boardWidth + 1, cellId + 1, cellId + boardWidth + 1);
    }

    private List<Integer> addTopandBottomCells(int cellId, int boardWidth) {
        return List.of(cellId - boardWidth, cellId + boardWidth);
    }
}

