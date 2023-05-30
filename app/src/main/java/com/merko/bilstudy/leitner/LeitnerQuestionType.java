package com.merko.bilstudy.leitner;

import androidx.annotation.NonNull;

public enum LeitnerQuestionType {
    TEXT_ASWER("Text Answer"),
    MULTIPLE_CHOICE_SINGLE("Multiple Choice(Single)");

    private final String str;

    LeitnerQuestionType(String str) {
        this.str = str;
    }

    @NonNull
    @Override
    public String toString() {
        return str;
    }
}
