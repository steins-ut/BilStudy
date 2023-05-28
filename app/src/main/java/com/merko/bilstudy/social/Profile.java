package com.merko.bilstudy.social;

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

    public Profile() {}

    /**
     * Copy constructor
     * @param original the object to copy
     */
    public Profile(Profile original) {
        this.uuid = original.uuid;
        this.name = original.name;
        this.imageUuid = original.imageUuid;
        this.coin = original.coin;
    }
}
