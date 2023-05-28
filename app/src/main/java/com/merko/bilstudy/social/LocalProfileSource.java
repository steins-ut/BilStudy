package com.merko.bilstudy.social;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.merko.bilstudy.utils.Globals;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class LocalProfileSource extends ProfileSource {

    private static final String PROFILE_PATH = "profile";
    private static final String PROFILE_FILE_NAME = "profile.json";

    private Profile profile;
    private File profileFile;
    private ObjectMapper mapper;

    public LocalProfileSource() {
        load();
    }

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public boolean save() {
        if(profileFile.exists()) {
            profileFile.delete();
        }
        try {
            mapper.writeValue(profileFile, profile);
        } catch(Exception e) {
            Log.e(toString(), e.getLocalizedMessage());
        }
        return true;
    }

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    protected boolean loadImpl() {
        mapper = new ObjectMapper();
        File rootFolder = Globals.getApplicationContext().getFilesDir();
        File profileFolder = new File(rootFolder, PROFILE_PATH);
        profileFolder.mkdirs();
        profileFile = new File(profileFolder, PROFILE_FILE_NAME);
        if(profileFile.isFile()) {
            try {
                profile = mapper.readValue(profileFile, Profile.class);
            } catch (Exception e) {
                Log.e(toString(), e.getLocalizedMessage());
            }
        }
        else {
            profile = new Profile();
            profile.uuid = UUID.randomUUID();
            profile.name = "User";
            profile.imageUuid = null;
            profile.coin = 0;
            saveIfAutoSave();
        }
        return true;
    }

    @Override
    protected boolean unloadImpl() {
        return true;
    }

    @Override
    public CompletableFuture<Profile> getLoggedInProfile() {
        return CompletableFuture.supplyAsync(() -> new Profile(profile));
    }

    @Override
    public CompletableFuture<Profile> getProfile(UUID id) {
        return CompletableFuture.supplyAsync(() -> new Profile(profile));
    }

    @Override
    public CompletableFuture<Boolean> updateProfile(Profile profile) {
        return CompletableFuture.supplyAsync(() -> {
            if(this.profile.uuid.equals(profile.uuid)) {
                this.profile = profile;
                saveIfAutoSave();
            }
            return true;
        });
    }
}
