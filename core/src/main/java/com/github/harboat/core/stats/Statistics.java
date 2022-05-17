package com.github.harboat.core.stats;

import lombok.Data;
import lombok.NonNull;

import java.util.Collection;
import java.util.List;

@Data
class Statistics {

    @NonNull
    private final Collection<PlayerStats> statistics;

    List<String> getStatistics() {
        return statistics.stream().map(s -> s.toString()).toList();
    }
}
