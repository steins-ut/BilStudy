package com.merko.bilstudy.NotepadDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.merko.bilstudy.NotepadModels.Notes;

@Database(entities = Notes.class, version = 1, exportSchema = false)
public abstract class NotepadRoomDB extends RoomDatabase {
    private static NotepadRoomDB database;
    private static String DATABASE_NAME = "NoteApp";

    public synchronized static NotepadRoomDB getInstance(Context context){
        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext(),
                    NotepadRoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract NotepadDAO notepadDAO();
}
