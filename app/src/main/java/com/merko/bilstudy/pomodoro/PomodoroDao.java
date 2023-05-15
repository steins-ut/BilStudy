package com.merko.bilstudy.pomodoro;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.UUID;

@Dao
public abstract class PomodoroDao {
    @Query("SELECT EXISTS(SELECT 1 FROM " + PomodoroPresetEntity.TABLE_NAME + " WHERE uuid = :presetId)")
    public abstract boolean hasPreset(UUID presetId);
    @Query("SELECT * FROM " + PomodoroPresetEntity.TABLE_NAME + " WHERE uuid = :presetId")
    public abstract PomodoroPreset getPreset(UUID presetId);
    @Query("SELECT * FROM " + PomodoroPresetEntity.TABLE_NAME)
    public abstract PomodoroPreset[] getAllPresets();
    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = PomodoroPresetEntity.class)
    public abstract void putPreset(PomodoroPreset preset);
    @Query("DELETE FROM " + PomodoroPresetEntity.TABLE_NAME + " WHERE uuid = :presetId")
    public abstract void deletePreset(UUID presetId);
}
