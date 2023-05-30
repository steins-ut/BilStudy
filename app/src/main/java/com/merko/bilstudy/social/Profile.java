package com.merko.bilstudy.social;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Class for storing profile data
 */
public class Profile {
    /** Identifier of the profile */
    public UUID uuid;
    /** Name of the user */
    public String name;
    /** Identifier of the profile image */
    public UUID imageUuid;
    /** Coins of the user */
    public int coin;
    public ArrayList<Integer>durations;
    public ArrayList<String>types;
    public ArrayList<String>folderNames;
    public ArrayList<Integer>allCards;
    public ArrayList<Integer>cardsStudied;
    public ArrayList<Integer>accuracy;
    public ArrayList<Integer> purchasedItems;


    public Profile() {}

    /**
     * Copy constructor
     * @param original the object to copy
     */
    public Profile(Profile original) {
        this.uuid = UUID.fromString(original.uuid.toString());
        this.name = original.name;
        this.imageUuid = original.imageUuid != null ? UUID.fromString(original.imageUuid.toString()) : null;
        this.coin = original.coin;
        this.purchasedItems = original.purchasedItems;

        this.types= original.types;
        this.durations = original.durations;

        this.folderNames = original.folderNames;
        this.allCards = original.allCards;
        this.accuracy = original.accuracy;
        this.cardsStudied = original.cardsStudied;
    }

    public void addToDurations(int duration) {
        durations.add(duration);
    }
    public void addToTypes(String type) {
        types.add(type);
    }
    public ArrayList<String> getTypesList() {
        return types;
    }
    public ArrayList<Integer> getDurationsList() {
        return durations;
    }
    public ArrayList<String> getFolderNamesList() {
        return folderNames;
    }

    public ArrayList<Integer> getAllCardsList() {
        return allCards;
    }
    public ArrayList<Integer> getCardsStudiedList() {
        return cardsStudied;
    }
    public ArrayList<Integer> getAccuracyList() {
        return accuracy;
    }
}
