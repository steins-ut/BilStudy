package com.merko.bilstudy.notepad;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
import java.util.UUID;

@Dao
public abstract class NotepadDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = NotesEntity.class)
    public abstract void insertNote(Notes notes);

    @Query("SELECT * FROM " + NotesEntity.TABLE_NAME)
    public abstract List<Notes> getAllNotes();

    @Query("UPDATE " + NotesEntity.TABLE_NAME + " SET title = :title, notes = :notes WHERE uuid = :uuid")
    public abstract void updateNote(UUID uuid, String title, String notes);

    @Query("DELETE FROM " + NotesEntity.TABLE_NAME + " WHERE uuid = :uuid")
    public abstract void deleteNote(UUID uuid);

    @Query("UPDATE " + NotesEntity.TABLE_NAME + " SET pinned = :pin WHERE uuid = :id")
    public abstract void pinNote(UUID id, boolean pin);
}
