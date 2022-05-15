package com.github.harboat.battleships.game;

import com.github.harboat.battleships.CoreQueueProducer;
import com.github.harboat.clients.core.game.GameCreation;
import com.github.harboat.clients.core.game.GameCreationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class GameService {

    private GameRepository repository;
    private CoreQueueProducer producer;
    @Transactional
    public void createGame(GameCreation gameCreation) {
        Game game = Game.builder()
                .playerIds(List.of(gameCreation.playerId()))
                .turnOfPlayer(gameCreation.playerId())
                .build();
        game = repository.save(game);
        producer.sendResponse(
                new GameCreationResponse(game.getId(), gameCreation.playerId())
        );
    }
}
