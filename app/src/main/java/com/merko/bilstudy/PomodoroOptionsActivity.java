package com.merko.bilstudy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.pomodoro.PomodoroPreset;
import com.merko.bilstudy.pomodoro.PomodoroSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PomodoroOptionsActivity extends AppCompatActivity {
    static List<PomodoroPreset> pomodoroOptions;
    RecyclerView recyclerView;
    PomodoroAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            Intent home = new Intent(getBaseContext(), MainActivity.class);
            startActivity(home);
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