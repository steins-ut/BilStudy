package com.merko.bilstudy.social;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LeitnerContainerStatistics {
    public Map<UUID, LeitnerQuestionStatistics> questionStatistics;

    public LeitnerContainerStatistics() {}

    public LeitnerContainerStatistics(LeitnerContainerStatistics original) {
        this.questionStatistics = new HashMap<>(original.questionStatistics);
    }
}
