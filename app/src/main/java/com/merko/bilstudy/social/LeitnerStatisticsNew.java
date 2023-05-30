package com.merko.bilstudy.social;

import java.util.Map;
import java.util.UUID;

public class LeitnerStatisticsNew {
    public long totalQuestionsSolved;
    public long totalTimeStudied;
    public Map<UUID, LeitnerContainerStatistics> containerStatistics;

    public LeitnerStatisticsNew() {}

    public LeitnerStatisticsNew(LeitnerStatisticsNew original) {
        this.totalQuestionsSolved = original.totalQuestionsSolved;
        this.totalTimeStudied = original.totalTimeStudied;
        //this.containerStatistics = original.containerStatistics.entrySet().stream();
    }
}
