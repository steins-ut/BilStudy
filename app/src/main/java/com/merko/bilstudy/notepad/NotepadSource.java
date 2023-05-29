package com.merko.bilstudy.notepad;

import com.merko.bilstudy.data.AbstractSource;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class NotepadSource extends AbstractSource {
    /**
     * Gets all notes
     *
     * @return all notes
     */
    public abstract CompletableFuture<List<Notes>> getAllNotes();

    /**
     * Gives the note a new id and stores it
     * @param notesEntity NotesEntity to store
     * @return New unique identifier for the note
     */
    public abstract CompletableFuture<UUID> insertNote(Notes notes);

    /**
     * Deletes the notes with the provided ids
     * @param notesEntity NotesEntity the note to delete
     */
    public abstract CompletableFuture<Void> deleteNote(UUID uuid);

    /**
     * Updates the note by changing the title and the note with the last input
     * @param id Unique identifier of the note
     * @param title Title of the note
     * @param note Content of the note
     */
    public abstract CompletableFuture<Void> updateNote(UUID id, String title, String note);

    /**
     * Places a pin icon on the right hand corner of the note
     * @param id Unique identifier of the note
     * @param pin pin if true, unpin if false
     */
    public abstract CompletableFuture<Void> pinNote(UUID id, boolean pin);
}
