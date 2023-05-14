package com.merko.bilstudy.data;

import android.util.Log;

import java.util.HashMap;

/**
 * Singleton class for separating
 * data providers from the rest of the code.
 */
public class ProviderLocator {

    private static ProviderLocator INSTANCE = null;

    private final HashMap<Class<? extends AbstractProvider>, AbstractProvider> providers;
    private final HashMap<Class<? extends AbstractProvider>, Class<? extends AbstractProvider>> preferredTypes;

    private ProviderLocator() {
        providers = new HashMap<>();
        preferredTypes = new HashMap<>();
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
     * @param providerClass Class object of T
     * @return The provider of type T
     * @param <T> Type of the provider (Must extend AbstractProvider)
     */
    @SuppressWarnings({"unchecked", "cast"})
    public <T extends AbstractProvider> T getProvider(Class<T> providerClass) {
        if(!providers.containsKey(providerClass)) {
            try {
                if(preferredTypes.containsKey(providerClass)) {
                    providers.put(providerClass, preferredTypes.get(providerClass).newInstance());
                }
                else {
                    providers.put(providerClass, providerClass.newInstance());
                }
            } catch (Exception e) {
                Log.e(toString(), String.format("Could not create new provider:\n%s", e.getMessage()));
            }
        }
        return (T)providers.get(providerClass);
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

    public <K extends AbstractProvider> void setPreferredType(Class<K> providerClass, Class<? extends K> preferredClass) {
        if(providerClass.isAssignableFrom(preferredClass)) {
            preferredTypes.put(providerClass, preferredClass);
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
