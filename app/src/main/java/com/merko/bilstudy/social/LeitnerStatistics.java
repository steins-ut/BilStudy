package com.merko.bilstudy.social;

public class LeitnerStatistics {
    String folder;
    int allCards;
    int cardsStudied;
    int accuracy;

    public LeitnerStatistics(String folder, int allCards, int cardsStudied, int accuracy) {
        this.folder = folder;
        this.allCards = allCards;
        this.cardsStudied = cardsStudied;
        this.accuracy = accuracy;
    }

    public String getFolder() {
        return folder;
    }

    public int getAllCards() {
        return allCards;
    }

    public int getCardsStudied() { return cardsStudied; }

    public int getAccuracy() { return accuracy; }

}
