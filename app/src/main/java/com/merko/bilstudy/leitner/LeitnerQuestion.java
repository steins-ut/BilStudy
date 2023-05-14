package com.merko.bilstudy.leitner;

import java.util.List;
import java.util.UUID;

public class LeitnerQuestion {
    public UUID uuid;
    public UUID containerId;
    public String question;
    public List<String> choices;
    public List<Integer> correctChoices;

    public LeitnerQuestion() {}

    public LeitnerQuestion(UUID uuid, String question, List<String> choices, List<Integer> correctChoices) {
        this.uuid = uuid;
        this.question = question;
        this.choices = choices;
        this.correctChoices = correctChoices;
    }
}
