package com.merko.bilstudy.pomodoro;

import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * The entity that is used to store pomodoro presets in
 * the Room database
 */
@Entity(tableName = PomodoroPresetEntity.TABLE_NAME,
        indices = {@Index(value = {"uuid"}, unique = true),
                    @Index("rowid")})
public class PomodoroPresetEntity extends PomodoroPreset {
    public static final String TABLE_NAME = "pomodoro";

    @PrimaryKey(autoGenerate = true)
    public int rowid;
}
