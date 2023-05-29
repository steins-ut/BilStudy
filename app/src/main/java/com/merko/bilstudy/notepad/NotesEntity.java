package com.merko.bilstudy.notepad;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.merko.bilstudy.pomodoro.PomodoroPresetEntity;

import java.io.Serializable;

/**
 * The entity that is used to store standard notes in
 * the Room database
 */
@Entity(tableName = NotesEntity.TABLE_NAME,
        indices = {@Index(value = {"uuid"}, unique = true),
                @Index(value = {"rowid"}, unique = true)})
public class NotesEntity extends Notes{
    public static final String TABLE_NAME = "note";

    @PrimaryKey(autoGenerate = true)
    public int rowid;
}
