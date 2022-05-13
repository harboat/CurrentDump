package com.github.harboat.core.games;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GameRepository extends MongoRepository<Game, String> {
    Optional<Game> findGameByGameIdAndPlayersContains(String gameId, String playerId);
}
