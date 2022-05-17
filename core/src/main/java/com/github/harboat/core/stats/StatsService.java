package com.github.harboat.core.stats;

import com.github.harboat.core.users.User;
import com.github.harboat.core.users.UserGetDTO;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private final statsRepository statsRepository;

    StatsService(statsRepository gameResultRepository) {
        this.statsRepository = gameResultRepository;
    }

    public String createStats(User player) {
        PlayerStats playerStats = new PlayerStats(player);
        statsRepository.save(playerStats);
//        Logger.info(String.format("User %s statistics created", playerStats.getPlayer()));
        return "create stats";
    }

    public StatisticsGetDTO getStats() {
        Statistics statistics = new Statistics(statsRepository.findAll());
//        Logger.info(String.format("Statistics provided"));
        return new StatisticsGetDTO(statistics.getStatistics());
    }

    public String updateStats(UserGetDTO user) {
        PlayerStats playerStats = statsRepository.findByPlayerEmail(user.getEmail()).get();
        playerStats.increment();
        statsRepository.save(playerStats);
//        Logger.info(String.format("User: %s statistics updated - winnings: %d",
//                playerStats.getPlayer().getName(), playerStats.getWinnings()));
        return String.format("%s - winnings: %d", playerStats.getPlayer().getName(), playerStats.getWinnings());
    }
}
