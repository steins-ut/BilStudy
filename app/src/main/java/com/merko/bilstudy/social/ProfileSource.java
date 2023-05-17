package com.merko.bilstudy.social;

import com.merko.bilstudy.data.AbstractSource;

import java.util.UUID;

public abstract class ProfileSource extends AbstractSource {
    /**
     * Returns the profile of the one currently using the app
     * @return profile of the user
     */
    public abstract Profile getLoggedInProfile();

    public abstract Profile getProfile(UUID id);
}
