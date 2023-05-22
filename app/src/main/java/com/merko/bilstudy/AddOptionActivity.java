package com.merko.bilstudy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.pomodoro.PomodoroPreset;
import com.merko.bilstudy.pomodoro.PomodoroSource;

public class AddOptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_option);

        EditText optionName = findViewById(R.id.optionNameInput);
        EditText studyDuration = findViewById(R.id.studyDurationInput);
        EditText breakDuration = findViewById(R.id.breakDurationInput);
        CardView createOption = findViewById(R.id.createOption);
        CardView backButton = findViewById(R.id.backButtonPomodoro);
        backButton.setOnClickListener((View view) ->
                startActivity(new Intent(getBaseContext(), PomodoroOptionsActivity.class)));

        createOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionName.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Option name can not be empty", Toast.LENGTH_LONG).show();
                }
                else if(studyDuration.getText().toString().isEmpty() || breakDuration.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Durations can not be empty", Toast.LENGTH_LONG).show();
                }
                else if(Integer.parseInt(studyDuration.getText().toString()) <= 0 || Integer.parseInt(breakDuration.getText().toString()) <= 0){
                    Toast.makeText(getApplicationContext(), "Durations should be positive numbers", Toast.LENGTH_LONG).show();
                }
                else{
                    saveOption(optionName.getText().toString(),
                            studyDuration.getText().toString(), breakDuration.getText().toString());
                }
            }
        });
    }

    public void saveOption(String optionName, String studyDuration, String breakDuration) {
        PomodoroSource pomodoro = SourceLocator.getInstance().getProvider(PomodoroSource.class);
        pomodoro.putPreset(
                new PomodoroPreset(null, optionName, Integer.parseInt(studyDuration), Integer.parseInt(breakDuration)));
        finish();
        Intent back = new Intent(this, PomodoroOptionsActivity.class);
        startActivity(back);
    }
}