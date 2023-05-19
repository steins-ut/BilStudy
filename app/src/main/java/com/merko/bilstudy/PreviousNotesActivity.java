package com.merko.bilstudy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.merko.bilstudy.Adapters.NotesListAdapter;
import com.merko.bilstudy.NotepadDatabase.NotepadRoomDB;
import com.merko.bilstudy.NotepadModels.Notes;

import java.util.ArrayList;
import java.util.List;

public class PreviousNotesActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    RecyclerView recyclerView;
    NotesListAdapter notesListAdapter;
    List<Notes> notes = new ArrayList<>();
    NotepadRoomDB database;
    FloatingActionButton newNoteButton;
    SearchView searchView_previous_notes;
    Notes selectedNote;
    private View card;
    private View back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_notes);

        recyclerView = findViewById(R.id.recycler_previous_notes);
        newNoteButton = findViewById(R.id.newNoteButton);
        searchView_previous_notes = findViewById(R.id.searchView_previous_notes);

        database = NotepadRoomDB.getInstance(this);
        notes = database.notepadDAO().getAll();

        updateRecycler(notes);

        newNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreviousNotesActivity.this, NotesTakerActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        searchView_previous_notes.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        back = findViewById(R.id.backButton);
        /*
        card = findViewById(R.id.new_note);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreviousNotesActivity.this, ChooseTemplateActivity.class);
                startActivity(intent);
            }
        });
        */
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreviousNotesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void filter(String newText) {
        List<Notes> filteredList = new ArrayList<>();
        for(Notes singleNote : notes){
            if(singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())
            || singleNote.getNotes().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(singleNote);
            }
        }
        notesListAdapter.filterList(filteredList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101){
            if(resultCode == Activity.RESULT_OK){
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                database.notepadDAO().insert(new_notes);
                notes.clear();
                notes.addAll(database.notepadDAO().getAll());
                notesListAdapter.notifyDataSetChanged();
            }
        }
        else if(requestCode == 102){
            if(resultCode == Activity.RESULT_OK){
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                database.notepadDAO().update(new_notes.getID(), new_notes.getTitle(), new_notes.getNotes());
                notes.clear();
                notes.addAll(database.notepadDAO().getAll());
                notesListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateRecycler(List<Notes> notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
        notesListAdapter = new NotesListAdapter(PreviousNotesActivity.this, notes, notesClickListener);
        recyclerView.setAdapter(notesListAdapter);
    }

    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClick(Notes notes) {
            Intent intent = new Intent(PreviousNotesActivity.this, NotesTakerActivity.class);
            intent.putExtra("old_note", notes);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {
            selectedNote = new Notes();
            selectedNote = notes;
            showPopup(cardView);
        }
    };

    private void showPopup(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.previous_notes_popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.imageView_pin:
                if(selectedNote.isPinned()){
                    database.notepadDAO().pin(selectedNote.getID(), false);
                    Toast.makeText(PreviousNotesActivity.this, "Unpinned!", Toast.LENGTH_SHORT).show();
                }
                else{
                    database.notepadDAO().pin(selectedNote.getID(), true);
                    Toast.makeText(PreviousNotesActivity.this, "Pinned!", Toast.LENGTH_SHORT).show();
                }

                notes.clear();
                notes.addAll(database.notepadDAO().getAll());
                notesListAdapter.notifyDataSetChanged();
                return true;

            case R.id.delete:
                database.notepadDAO().delete(selectedNote);
                notes.remove(selectedNote);
                notesListAdapter.notifyDataSetChanged();
                Toast.makeText(PreviousNotesActivity.this, "Note Deleted!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }
}