package com.merko.bilstudy.social;

import com.merko.bilstudy.data.AbstractSource;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class ProfileSource extends AbstractSource {
    /**
     * Returns the profile of the one currently using the app
     * @return profile of the user
     */
    public abstract CompletableFuture<Profile> getLoggedInProfile();
    public abstract CompletableFuture<Profile> getProfile(UUID id);
    public abstract CompletableFuture<Boolean> updateProfile(Profile profile);
}
