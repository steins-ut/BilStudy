package com.merko.bilstudy.social;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LeitnerBoxStatistics {
    public Map<UUID, LeitnerQuestionStatistics> questionStatistics;

    public LeitnerBoxStatistics() {
        questionStatistics = new HashMap<>();
    }

    public LeitnerBoxStatistics(LeitnerBoxStatistics original) {
        this.questionStatistics = new HashMap<>(original.questionStatistics);
    }
}
