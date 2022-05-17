package com.github.harboat.core.stats;

import com.github.harboat.clients.exceptions.ResourceNotFound;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private final statsRepository statsRepository;

    StatsService(statsRepository gameResultRepository) {
        this.statsRepository = gameResultRepository;
    }

    public String createStats(String playerId, String playerName) {
        PlayerStats playerStats = new PlayerStats(playerId, playerName);
        statsRepository.save(playerStats);
//        Logger.info(String.format("User %s statistics created", playerStats.getPlayer()));
        return "create stats";
    }

    StatisticsGetDTO getStats() {
        Statistics statistics = new Statistics(statsRepository.findAll());
//        Logger.info(String.format("Statistics provided"));
        return new StatisticsGetDTO(statistics.getStatistics());
    }

    public void updateStats(String playerId) {
        PlayerStats playerStats = statsRepository.findByPlayerId(playerId).
                orElseThrow(() -> new ResourceNotFound("User not found!"));
        playerStats.incrementWinnings();
        statsRepository.save(playerStats);
//        Logger.info(String.format("User: %s statistics updated - winnings: %d",
//                playerStats.getPlayerName(), playerStats.getWinnings()));
    }

    public void updateStats(String playerId, int shot) {
        PlayerStats playerStats = statsRepository.findByPlayerId(playerId).
                orElseThrow(() -> new ResourceNotFound("User not found!"));
        playerStats.incrementShots(shot);
    }
}
