package com.merko.bilstudy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PomodoroOptionsActivity extends AppCompatActivity {
    CardView backPomodoro;
    CardView addPomodoro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro_options);

        backPomodoro = findViewById(R.id.backPomodoro);
        addPomodoro = findViewById(R.id.addPomodoro);

        backPomodoro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(getBaseContext(), MainActivity.class);
                startActivity(homepage);
            }
        });

    }
}