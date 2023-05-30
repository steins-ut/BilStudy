package com.merko.bilstudy.notepad;

import java.io.Serializable;
import java.util.UUID;

public class Notes implements Serializable {

    public UUID uuid;
    public String title = "";
    public String notes = "";
    public String date = "";
    private boolean pinned = false; //to pin create a pin icon on the right hand corner
    public Notes(){}
    public Notes(UUID uuid, String title, String notes, String date){
        this.uuid = uuid;
        this.title = title;
        this.notes = notes;
        this.date = date;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getDate() {
        return date;
    }

    public boolean isPinned() {
        return pinned;
    }

    public String getTitle() {
        return title;
    }

    public String getNotes() {
        return notes;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }
}
