package com.merko.bilstudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.dialog.PopUpDialog;
import com.merko.bilstudy.pomodoro.PomodoroPreset;
import com.merko.bilstudy.pomodoro.PomodoroSource;
import com.merko.bilstudy.social.Profile;
import com.merko.bilstudy.social.ProfileSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PomodoroOptionsActivity extends AppCompatActivity {
    static List<PomodoroPreset> pomodoroOptions;
    RecyclerView recyclerView;
    PomodoroAdapter adapter;
    long startTime;
    long endTime;
    long difference;

    long minutesPassed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startTime = System.currentTimeMillis();
        setContentView(R.layout.activity_pomodoro_options);
        recyclerView = findViewById(R.id.PomodoroRecyclerView);
        pomodoroOptions = new ArrayList<PomodoroPreset>();
        loadOptions();
        CardView addButton = findViewById(R.id.addPomodoro);
        CardView backButton = findViewById(R.id.backButtonPomodoro);

        adapter = new PomodoroAdapter(getApplicationContext(), pomodoroOptions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener((View view)  -> {
            Intent createOption = new Intent(getBaseContext(), AddOptionActivity.class);
            startActivity(createOption);
        });

        backButton.setOnClickListener((View view) -> {
            endTime = System.currentTimeMillis();
            difference = endTime - startTime;
            minutesPassed = difference / 60000;
            try {
                Profile p = SourceLocator.getInstance().getSource(ProfileSource.class).getLoggedInProfile().get();
                p.coin += minutesPassed / 30 * 15;
                ProfileSource s = SourceLocator.getInstance().getSource(ProfileSource.class);
                s.updateProfile(p).join();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(minutesPassed / 30 * 15 > 0){
                PopUpDialog d = new PopUpDialog(PomodoroOptionsActivity.this, R.style.Theme_BilStudy_Pomodoro_PopUp, "Pomodoro Timer", minutesPassed / 30 * 15);
                d.show();
            }
            else{
                Intent home = new Intent(PomodoroOptionsActivity.this, MainActivity.class);
                startActivity(home);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadOptions();
    }

    public static void loadOptions() {
        PomodoroSource pomodoro = SourceLocator.getInstance().getSource(PomodoroSource.class);
        if(!pomodoro.equals(null)){
            CompletableFuture<PomodoroPreset[]> all = pomodoro.getAllPresets();
            pomodoroOptions = Arrays.asList(all.join());
        }
    }
}