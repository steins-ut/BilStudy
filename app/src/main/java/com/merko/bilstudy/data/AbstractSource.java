package com.merko.bilstudy.data;

import android.util.Log;

/**
 * This class is the base for all providers used for
 * getting data.
 */
public abstract class AbstractSource {
    protected boolean autoSave = true;
    protected boolean saveOnUnload = true;
    protected boolean loadOnce = false;
    protected boolean loaded = false;

    /**
     * Loads the provider. (This is not automatically done, doing so is up to
     * the derived classes.)
     * @return true if successfully loaded, false if not
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean load() {
        if(!loaded) {
            if(loadImpl()) {
                loaded = true;
                return true;
            }
            Log.e(toString(), "An error occurred while loading.");
        }
        else {
            if(!loadOnce) {
                if(loadImpl()) {
                    loaded = true;
                    return true;
                }
                Log.e(toString(), "An error occurred while loading.");
            }
            Log.e(toString(),"Provider is already loaded and set to load once.");
        }
        return false;
    }

    /**
     * Saves changes in the provider.
     * @return true if save is successful, false if not
     */
    public abstract boolean save();

    /**
     * Unloads the provider. (Actuall implementation is not here.)
     * {@link #unloadImpl() Implementation is done here.}
     * @return true if unloading is successful, false if not
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean unload() {
        if(loaded) {
            if(saveOnUnload) {
                Log.d(toString(), "Save on unload is enabled, saving...");
                save();
            }
            if(unloadImpl()) {
                loaded = false;
                return true;
            }
            Log.e(toString(), "An error occurred while unloading.");
        }
        Log.e(toString(), "Cannot unload provider. It is not loaded.");
        return false;
    }

    /**
     * Actual loading function. Derived classes
     * should override this.
     * @return true if loading is successful, false if not
     */
    protected abstract boolean loadImpl();

    /**
     * Actual unloading function. Derived classes
     * should override this.
     * @return true if unloading is successful, false if not
     */
    protected abstract boolean unloadImpl();

    /**
     * Saves changes if auto-save is enabled. For internal
     * use in the provider.
     * @return true if saving is successful, false if not
     */
    @SuppressWarnings("UnusedReturnValue")
    protected boolean saveIfAutoSave() {
        if(autoSave) {
            Log.d(toString(), "Auto-saving is enabled, saving...");
            return save();
        }
        return false;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        unload();
    }
}
