package com.merko.bilstudy.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.merko.bilstudy.utils.Globals;
import com.merko.bilstudy.pomodoro.PomodoroDao;
import com.merko.bilstudy.pomodoro.PomodoroPresetEntity;

/**
 * Database class using Room persistent storage for
 * storing data locally.
 */
@Database(entities = {PomodoroPresetEntity.class}, exportSchema = false, version = 1)
@TypeConverters({RoomTypeConverters.class})
public abstract class BilStudyDatabase extends RoomDatabase {

    private static BilStudyDatabase INSTANCE = null;
    public static final String DATABASE_NAME = "data";

    public static BilStudyDatabase getInstance() {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(Globals.getApplicationContext(),
                    BilStudyDatabase.class,
                    DATABASE_NAME).build();
        }

        return INSTANCE;
    }

    public abstract PomodoroDao pomodoroDao();
}