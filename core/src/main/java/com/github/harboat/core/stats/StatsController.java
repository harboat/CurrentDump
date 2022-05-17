package com.github.harboat.core.stats;

import com.github.harboat.core.users.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("api/v1/stats")
class StatsController {

    private final StatsService statsService;
    private final UserService userService;

    StatsController(StatsService gameResultsService, UserService userService) {
        this.statsService = gameResultsService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<StatisticsGetDTO> getStats() {
        StatisticsGetDTO statisticsGetDTO = statsService.getStats();
        return new ResponseEntity<>(statisticsGetDTO, HttpStatus.OK);
    }
}
