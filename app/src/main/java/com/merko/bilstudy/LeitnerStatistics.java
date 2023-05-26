package com.merko.bilstudy;

import android.widget.TextView;

public class LeitnerStatistics {

    String folder;
    String allCards;
    String cardsStudied;
    String accuracy;

    public LeitnerStatistics(String folder, String allCards, String cardsStudied, String accuracy) {
        this.folder = folder;
        this.allCards = allCards;
        this.cardsStudied = cardsStudied;
        this.accuracy = accuracy;
    }

    public String getFolder() {
        return folder;
    }

    public String getAllCards() {
        return allCards;
    }

    public String getCardsStudied() { return cardsStudied; }

    public String getAccuracy() { return accuracy; }

}
