package com.merko.bilstudy.data;

import android.util.Log;

import java.util.HashMap;

/**
 * Singleton class for separating
 * data providers from the rest of the code.
 */
public class ProviderLocator {

    private static ProviderLocator INSTANCE = null;

    private final HashMap<Class<?>, AbstractProvider> providers;

    private ProviderLocator() {
        providers = new HashMap<>();
    }

    public static ProviderLocator getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ProviderLocator();
        }
        return INSTANCE;
    }

    /**
     * Returns provider of type T if it exists, creates one if
     * it doesn't and returns the new provider.
     * @param clazz Class object of T
     * @return The provider of type T
     * @param <T> Type of the provider (Must extend AbstractProvider)
     */
    @SuppressWarnings({"unchecked", "cast"})
    public <T extends AbstractProvider> T getProvider(Class<T> clazz) {
        if(!providers.containsKey(clazz)) {
            try {
                providers.put(clazz, clazz.newInstance());
            } catch (Exception e) {
                Log.e(toString(), String.format("Could not create new provider:\n%s", e.getMessage()));
            }
        }
        return (T)providers.get(clazz);
    }

    /**
     * Sets the provider of type T in the locator to the
     * provided one.
     * @param clazz Class object of T
     * @param provider The provider to put in the locator
     * @param <T> Type of the provider (Must extend AbstractProvider)
     */
    public <T extends AbstractProvider> void setProvider(Class<T> clazz, AbstractProvider provider) {
        if(clazz.isAssignableFrom(provider.getClass())) {
            providers.put(clazz, provider);
        }
    }

    /**
     * Removes provider of type T from the locator.
     * @param clazz Class object of T
     * @param <T> Type of the provider (Must extend AbstractProvider)
     */
    public <T extends AbstractProvider> void removeProvider(Class<T> clazz) {
        if(!providers.containsKey(clazz)) {
            providers.remove(clazz);
        }
    }

}
