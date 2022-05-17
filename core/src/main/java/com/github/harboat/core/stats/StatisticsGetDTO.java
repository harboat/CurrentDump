package com.github.harboat.core.stats;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
class StatisticsGetDTO {

    @NonNull
    private final List<String> statistics;
}
