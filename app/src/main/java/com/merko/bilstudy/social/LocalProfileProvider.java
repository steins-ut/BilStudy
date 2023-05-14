package com.merko.bilstudy.social;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.merko.bilstudy.utils.Globals;

import java.io.File;
import java.util.UUID;

public class LocalProfileProvider extends ProfileProvider {

    private static final String PROFILE_PATH = "profile";
    private static final String PROFILE_FILE_NAME = "profile.json";

    private Profile profile;
    private File profileFile;
    private ObjectMapper mapper;

    public LocalProfileProvider() {
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
            saveIfAutoSave();
        }
        return true;
    }

    @Override
    protected boolean unloadImpl() {
        return true;
    }

    @Override
    public Profile getLoggedInProfile() { return new Profile(profile); }

    @Override
    public Profile getProfile(UUID id) {
        return new Profile(profile);
    }

    public void setUserProfile(Profile profile) {
        this.profile = profile;
        saveIfAutoSave();
    }
}
