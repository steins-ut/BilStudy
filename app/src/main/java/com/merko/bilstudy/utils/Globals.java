package com.merko.bilstudy.utils;

import android.content.Context;
/**
 * Utility class for keeping values
 * that are going to be valid and used throughout
 * most of the application's lifetime.
 */
public class Globals {
    private static Context APPLICATION_CONTEXT = null;
    public static void setApplicationContext(Context ctx) {
        APPLICATION_CONTEXT = ctx.getApplicationContext();
    }
    public static Context getApplicationContext() {
        if(APPLICATION_CONTEXT == null) {
            throw new RuntimeException("Application context was not set.");
        }
        return APPLICATION_CONTEXT;
    }

    public static final String PREFERENCES_NAME = "preferences";
    public static final String PREFERENCES_VERSION_CODE_KEY = "version_code";
    public static final int LEITNER_FREQUENCY_CHANGE_COUNT = 3;
    public static final int DAY_TO_SECONDS = 86400000;
}
