package com.github.harboat.configuration;

import com.github.harboat.clients.configuration.*;
import com.github.harboat.clients.core.board.Size;
import com.github.harboat.clients.core.placement.GamePlacement;
import com.github.harboat.clients.exceptions.BadRequest;
import com.github.harboat.clients.exceptions.ResourceNotFound;
import com.github.harboat.clients.rooms.MarkFleetSet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfigurationService {

    private ConfigurationRepository repository;
    private RoomsQueueProducer roomsQueueProducer;
    private CoreQueueProducer coreQueueProducer;
    private GameQueueProducer gameQueueProducer;

    void create(ConfigurationCreate configurationCreate) {
        Size size = new Size(10, 10);
        repository.save(
            Configuration.builder()
                    .roomId(configurationCreate.roomId())
                    .ownerId(configurationCreate.playerId())
                    .size(size)
                    .playersConfiguration(
                            Map.of(
                                    configurationCreate.playerId(),
                                    Player.builder()
                                            .ships(List.of())
                                            .build()
                            )
                    )
                    .build()
        );
        coreQueueProducer.sendSize(
                new SetGameSize(configurationCreate.roomId(), configurationCreate.playerId(), size)
        );
    }

    @Transactional
    void setSize(SetGameSize setGameSize) {
        Configuration configuration = getOwnerConfigurationFromRequest(setGameSize.roomId(), setGameSize.playerId());
        Map<String, Player> playersConfiguration = configuration.getPlayersConfiguration();
        playersConfiguration.keySet()
                .forEach(p ->
                        playersConfiguration.put(
                                p,
                                Player.builder()
                                        .ships(List.of())
                                        .build()
                        )
                );
        configuration.setSize(setGameSize.size());
        coreQueueProducer.sendSize(
                setGameSize
        );
    }

    @Transactional
    void markShipPlacement(SetShipsPosition setShipsPosition) {
        Configuration configuration = getPlayerConfigurationFormRequest(setShipsPosition.roomId(), setShipsPosition.playerId());
        configuration.getPlayersConfiguration().put(
                setShipsPosition.playerId(),
                Player.builder()
                        .ships(setShipsPosition.ships())
                        .build()
        );
        roomsQueueProducer.sendPlacement(
                new MarkFleetSet(
                        setShipsPosition.roomId(),
                        setShipsPosition.playerId()
                )
        );
    }

    void createGame(CreateGame createGame) {
        Configuration configuration = getOwnerConfigurationFromRequest(createGame.roomId(), createGame.playerId());
        // TODO: create game with full config
        System.out.println(configuration);
    }

    private Configuration getPlayerConfigurationFormRequest(String roomId, String playerId) {
        Optional<Configuration> configuration = repository.findByRoomIdAndPlayersConfigurationContaining(roomId, playerId);
        if (configuration.isEmpty()) throw new BadRequest("You are not in this room!");
        return configuration.get();
    }

    private Configuration getOwnerConfigurationFromRequest(String roomId, String playerId) {
        Optional<Configuration> configuration = repository.findByRoomIdAndOwnerId(roomId, playerId);
        if (configuration.isEmpty()) throw new BadRequest("You are not an owner for this room!");
        return configuration.get();
    }

}
