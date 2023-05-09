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
}
