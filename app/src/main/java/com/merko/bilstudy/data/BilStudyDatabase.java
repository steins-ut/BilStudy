package com.merko.bilstudy.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.merko.bilstudy.leitner.LeitnerContainerEntity;
import com.merko.bilstudy.leitner.LeitnerDao;
import com.merko.bilstudy.leitner.LeitnerQuestionEntity;
import com.merko.bilstudy.notepad.NotepadDao;
import com.merko.bilstudy.notepad.NotesEntity;
import com.merko.bilstudy.pomodoro.PomodoroDao;
import com.merko.bilstudy.pomodoro.PomodoroPresetEntity;
import com.merko.bilstudy.utils.Globals;

/**
 * Database class using Room persistent storage for
 * storing data locally.
 */
@Database(entities = {PomodoroPresetEntity.class,
                    LeitnerQuestionEntity.class,
                    LeitnerContainerEntity.class,
                    NotesEntity.class
                    }, exportSchema = false, version = 2)
@TypeConverters({RoomTypeConverters.class})
public abstract class BilStudyDatabase extends RoomDatabase {

    private static BilStudyDatabase INSTANCE = null;
    public static final String DATABASE_NAME = "data";

    public static BilStudyDatabase getInstance() {
        if(INSTANCE == null) {
            //WARNING: allowMainThreadQueries is ONLY TEMPORARY.
            //Room should not be queried from the main thread, this is
            //to make development and testing easier. allowMainThreadQueries will
            //be removed when it is time.
            INSTANCE = Room.databaseBuilder(Globals.getApplicationContext(),
                    BilStudyDatabase.class,
                    DATABASE_NAME)
                    .createFromAsset("testdb.db")
                    .build();
        }

        return INSTANCE;
    }

    public abstract PomodoroDao pomodoroDao();
    public abstract LeitnerDao leitnerDao();
    public abstract NotepadDao notepadDao();
}
