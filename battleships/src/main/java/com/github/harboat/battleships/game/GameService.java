package com.github.harboat.battleships.game;

import com.github.harboat.battleships.CoreQueueProducer;
import com.github.harboat.battleships.board.BoardService;
import com.github.harboat.clients.core.game.GameCreation;
import com.github.harboat.clients.core.game.GameCreationResponse;
import com.github.harboat.clients.core.game.PlayerJoin;
import com.github.harboat.clients.core.game.PlayerJoinedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository repository;
    private final BoardService boardService;
    private final CoreQueueProducer producer;
    private Random random = new SecureRandom();
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
        List<String> playerIds = (List<String>) game.getPlayerIds();
        playerIds.add(playerJoin.playerId());
        String startingPlayerId = playerIds.get(random.nextInt(0, 2));
        game.setPlayerIds(playerIds);
        game.setTurnOfPlayer(startingPlayerId);
        repository.save(game);
        boardService.createBoardForSecondPlayer(
                game.getId(), playerJoin.playerId()
        );
        producer.sendResponse(
                new PlayerJoinedResponse(game.getId(), playerJoin.playerId(), startingPlayerId)
        );
    }


    public String getEnemyId(String gameId, String playerId) {
        return repository.findById(gameId).orElseThrow()
                .getPlayerIds().stream()
                .filter(pid -> !pid.equals(playerId))
                .findFirst().orElseThrow();
    }
}
