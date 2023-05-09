package com.merko.bilstudy.pomodoro;

import com.merko.bilstudy.data.AbstractProvider;

import java.util.UUID;

public abstract class PomodoroProvider extends AbstractProvider {
    /**
     * Get preset with the provided id
     * @param presetId Unique identifier of the preset
     * @return Preset with the provided id
     */
    public abstract PomodoroPreset getPreset(UUID presetId);

    /**
     * Gets all presets
     * @return all presets
     */
    public abstract PomodoroPreset[] getAllPresets();

    /**
     * Gives the preset a new id and stores it
     * @param preset Preset to store
     * @return New unique identifier for the preset
     */
    public abstract UUID putPreset(PomodoroPreset preset);

    /**
     * Deletes the presets with the provided ids
     * @param presetId Identifier of the preset to delete
     */
    public abstract void deletePreset(UUID presetId);
}
