package com.github.harboat.battleships.game;

import com.github.harboat.battleships.CoreQueueProducer;
import com.github.harboat.battleships.board.BoardService;
import com.github.harboat.clients.core.game.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class GameService {

    private final GameRepository repository;
    private final BoardService boardService;
    private final CoreQueueProducer producer;
    private final Random random = new SecureRandom();
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

    public void playerJoin(PlayerJoin playerJoin) {
        Game game = repository.findById(playerJoin.gameId()).orElseThrow();
        Collection<String> playerIds = game.getPlayerIds();
        playerIds.add(playerJoin.playerId());
        game.setPlayerIds(playerIds);
        repository.save(game);
        boardService.createBoardForSecondPlayer(
                game.getId(), playerJoin.playerId()
        );
        producer.sendResponse(
                new PlayerJoinedResponse(game.getId(), playerJoin.playerId())
        );
    }

    public void start(GameStart gameStart) {
        Game game = repository.findById(gameStart.gameId()).orElseThrow();
        List<String> playerIds = (List<String>) game.getPlayerIds();
        String startingPlayerId = playerIds.get(random.nextInt(0, 2));
        game.setTurnOfPlayer(startingPlayerId);
        producer.sendResponse(
                new GameStartResponse(game.getId(), startingPlayerId)
        );
    }
}
