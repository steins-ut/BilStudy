package com.merko.bilstudy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.dialog.PopUpDialog;
import com.merko.bilstudy.social.Profile;
import com.merko.bilstudy.social.ProfileSource;

import java.util.concurrent.ExecutionException;


public class ChooseTemplateActivity extends AppCompatActivity {
    private View back;
    private View mindMaps;
    private View standardNotes;
    private View toDoList;
    long startTime;
    long endTime;
    long difference;
    long minutesPassed;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        startTime = System.currentTimeMillis();
        setContentView(R.layout.activity_choose_template);
        back = findViewById(R.id.back_button);
        mindMaps = findViewById(R.id.mind_maps);
        standardNotes = findViewById(R.id.blank_note);
        toDoList = findViewById(R.id.todo);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = System.currentTimeMillis();
                difference = endTime - startTime;
                minutesPassed = difference / 60000;
                try {
                    Profile p = SourceLocator.getInstance().getSource(ProfileSource.class).getLoggedInProfile().get();
                    p.coin += minutesPassed / 30 * 10;
                    if(minutesPassed > 0){
                        p.durations.add((int) minutesPassed);
                        p.types.add("notepad");
                    }

                    if(minutesPassed / 30 * 10 > 0){
                        PopUpDialog d = new PopUpDialog(ChooseTemplateActivity.this, R.style.Theme_BilStudy_Notepad_PopUp, "Notepad", minutesPassed / 30 * 10);
                        d.show();
                    }

                    else{
                        Intent home = new Intent(ChooseTemplateActivity.this, MainActivity.class);
                        startActivity(home);
                    }

                    ProfileSource s = SourceLocator.getInstance().getSource(ProfileSource.class);
                    s.updateProfile(p).join();
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        standardNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseTemplateActivity.this, PreviousNotesActivity.class);
                startActivity(intent);
            }
        });
        mindMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseTemplateActivity.this, MindMapActivity.class);
                startActivity(intent);
            }
        });

        toDoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseTemplateActivity.this, ToDoListActivity.class);
                startActivity(intent);
            }
        });

    }

}