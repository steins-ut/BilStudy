package com.merko.bilstudy.social;

public class LeitnerQuestionStatistics {
    public enum Frequency {
        WEEKLY(0, 7),
        THRIDAILY(1, 3),
        DAILY(2, 1);

        private int index;
        private int dayInterval;

        private Frequency(int index, int dayInterval) {
            this.index = index;
            this.dayInterval = dayInterval;
        }

        public Frequency getNextFrequency() {
            int nextIndex = index < values().length - 1 ? index + 1 : index;
            return values()[nextIndex];
        }

        public Frequency getPreviousFrequency() {
            int previousIndex = index > 0 ? index - 1 : index;
            return values()[previousIndex];
        }

        public int getDayInterval() {
            return dayInterval;
        }
    }

    public enum Solved {
        CORRECT,
        INCORRECT,
        UNSOLVED
    }

    public Solved solved;
    public Frequency frequency;
    public long solveDate;
    public int correctCount;

    public LeitnerQuestionStatistics() {
        solved = Solved.UNSOLVED;
        frequency = Frequency.WEEKLY;
        solveDate = -1;
        correctCount = 0;
    }
}
