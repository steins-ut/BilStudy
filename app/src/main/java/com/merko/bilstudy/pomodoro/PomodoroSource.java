package com.merko.bilstudy.pomodoro;

import com.merko.bilstudy.data.AbstractSource;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class PomodoroSource extends AbstractSource {
    /**
     * Get preset with the provided id
     * @param id Unique identifier of the preset
     * @return Preset with the provided id
     */
    public abstract CompletableFuture<PomodoroPreset> getPreset(UUID id);

    /**
     * Gets all presets
     * @return all presets
     */
    public abstract CompletableFuture<PomodoroPreset[]> getAllPresets();

    /**
     * Gives the preset a new id and stores it
     * @param preset Preset to store
     * @return New unique identifier for the preset
     */
    public abstract CompletableFuture<UUID> putPreset(PomodoroPreset preset);

    /**
     * Deletes the presets with the provided ids
     * @param id Identifier of the preset to delete
     */
    public abstract CompletableFuture<Void> deletePreset(UUID id);
}
