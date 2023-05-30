package com.merko.bilstudy.social;

import java.util.ArrayList;
import java.util.List;
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

    public List<Integer> purchasedItems;

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
    }
}
