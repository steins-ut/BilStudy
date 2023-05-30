package com.merko.bilstudy.social;

import java.util.Date;

public class LeitnerQuestionStatistics {
    public enum Frequency {
        WEEKLY,
        THRIDAILY,
        DAILY
    }

    public enum Solved {
        CORRECT,
        INCORRECT,
        UNSOLVED
    }

    public Solved solved;
    public Frequency frequency;
    public Date lastSolved;

    public LeitnerQuestionStatistics() {}

    public LeitnerQuestionStatistics(LeitnerQuestionStatistics original) {
        this.solved = original.solved;
        this.frequency = original.frequency;
        this.lastSolved = original.lastSolved;
    }
}
