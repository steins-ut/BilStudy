package com.merko.bilstudy.social;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LeitnerStatistics {
    public long totalQuestionsSolved;
    public long totalTimeStudied;
    public Map<UUID, LeitnerBoxStatistics> containerStatistics;

    public LeitnerStatistics() {
        containerStatistics = new HashMap<>();
    }

    public LeitnerStatistics(LeitnerStatistics original) {
        this.totalQuestionsSolved = original.totalQuestionsSolved;
        this.totalTimeStudied = original.totalTimeStudied;
        this.containerStatistics = new HashMap<>(original.containerStatistics);
    }
}
