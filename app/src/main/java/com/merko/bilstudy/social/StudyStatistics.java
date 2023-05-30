package com.merko.bilstudy.social;

public class StudyStatistics {
    public LeitnerStatisticsNew leitnerStatistics;

    public StudyStatistics() {}

    public StudyStatistics(StudyStatistics original) {
        this.leitnerStatistics = new LeitnerStatisticsNew(leitnerStatistics);
    }
}
