package com.merko.bilstudy.notepad;

import com.merko.bilstudy.data.BilStudyDatabase;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class RoomNotepadSource extends NotepadSource{


    @Override
    public boolean save() {
        return false;
    }

    @Override
    protected boolean loadImpl() {
        return false;
    }

    @Override
    protected boolean unloadImpl() {
        return false;
    }


    @Override
    public CompletableFuture<List<Notes>> getAllNotes() {
        return CompletableFuture.supplyAsync(() -> BilStudyDatabase.getInstance().notepadDao().getAllNotes());
    }

    @Override
    public CompletableFuture<UUID> insertNote(Notes notes) {
        return CompletableFuture.supplyAsync(() -> {
            notes.uuid = UUID.randomUUID();
            BilStudyDatabase.getInstance().notepadDao().insertNote(notes);
            return notes.uuid;
        });
    }

    @Override
    public CompletableFuture<Void> deleteNote(UUID uuid) {
        return CompletableFuture.runAsync(() -> BilStudyDatabase.getInstance().notepadDao().deleteNote(uuid));
    }

    @Override
    public CompletableFuture<Void> updateNote(UUID id, String title, String note) {
        return CompletableFuture.runAsync(() -> BilStudyDatabase.getInstance().notepadDao().updateNote(id, title, note));
    }

    @Override
    public CompletableFuture<Void> pinNote(UUID id, boolean pin) {
        return CompletableFuture.runAsync(() -> BilStudyDatabase.getInstance().notepadDao().pinNote(id, pin));
    }
}
