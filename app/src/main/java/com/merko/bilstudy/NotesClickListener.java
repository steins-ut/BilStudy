package com.merko.bilstudy;

import androidx.cardview.widget.CardView;

import com.merko.bilstudy.NotepadModels.Notes;

public interface NotesClickListener {
    void onClick(Notes notes);
    void onLongClick(Notes notes, CardView cardView);
}
