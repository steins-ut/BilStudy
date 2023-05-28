package com.merko.bilstudy.data;

import android.util.Log;

import java.util.HashMap;

/**
 * Singleton class for separating
 * data sources from the rest of the code.
 */
public class SourceLocator {

    private static SourceLocator INSTANCE = null;

    private final HashMap<Class<? extends AbstractSource>, AbstractSource> sources;
    private final HashMap<Class<? extends AbstractSource>, Class<? extends AbstractSource>> preferredTypes;

    private SourceLocator() {
        sources = new HashMap<>();
        preferredTypes = new HashMap<>();
    }

    public static SourceLocator getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new SourceLocator();
        }
        return INSTANCE;
    }

    /**
     * Returns source of type T if it exists, creates one if
     * it doesn't and returns the new source.
     * @param sourceClass Class object of T
     * @return The source of type T
     * @param <T> Type of the source (Must extend AbstractProvider)
     */
    @SuppressWarnings({"unchecked", "cast"})
    public <T extends AbstractSource> T getSource(Class<T> sourceClass) {
        if(!sources.containsKey(sourceClass)) {
            try {
                sources.put(sourceClass, preferredTypes.getOrDefault(sourceClass, sourceClass).newInstance());
            } catch (Exception e) {
                Log.e(toString(), String.format("Could not create new source:\n%s", e.getMessage()));
            }
        }
        return (T)sources.get(sourceClass);
    }

    /**
     * Sets the source of type T in the locator to the
     * provided one.
     * @param clazz Class object of T
     * @param source The source to put in the locator
     * @param <T> Type of the source (Must extend AbstractProvider)
     */
    public <T extends AbstractSource> void setSource(Class<T> clazz, AbstractSource source) {
        if(clazz.isAssignableFrom(source.getClass())) {
            sources.put(clazz, source);
        }
    }

    public <K extends AbstractSource> void setPreferredType(Class<K> sourceClass, Class<? extends K> preferredClass) {
        if(sourceClass.isAssignableFrom(preferredClass)) {
            preferredTypes.put(sourceClass, preferredClass);
        }
    }

    /**
     * Removes source of type T from the locator.
     * @param clazz Class object of T
     * @param <T> Type of the source (Must extend AbstractProvider)
     */
    public <T extends AbstractSource> void removeSource(Class<T> clazz) {
        if(!sources.containsKey(clazz)) {
            sources.remove(clazz);
        }
    }

}
