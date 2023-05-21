package com.merko.bilstudy.notepad;

import androidx.cardview.widget.CardView;

import com.merko.bilstudy.notepad.Notes;

public interface NotesClickListener {
    void onClick(Notes notes);
    void onLongClick(Notes notes, CardView cardView);
}
