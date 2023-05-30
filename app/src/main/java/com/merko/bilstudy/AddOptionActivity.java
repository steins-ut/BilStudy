package com.merko.bilstudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.pomodoro.PomodoroPreset;
import com.merko.bilstudy.pomodoro.PomodoroSource;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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

        createOption.setOnClickListener((View view) -> {
                if(optionName.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Option name can not be empty", Toast.LENGTH_LONG).show();
                }
                PomodoroSource pomodoro = SourceLocator.getInstance().getSource(PomodoroSource.class);
                boolean sameName = false;
                if(!pomodoro.equals(null)){
                    CompletableFuture<PomodoroPreset[]> all = pomodoro.getAllPresets();
                    List<PomodoroPreset> pomodoroOptions = Arrays.asList(all.join());
                    for(int i = 0; i < pomodoroOptions.size(); i++){
                        if(pomodoroOptions.get(i).name.equals(optionName.getText().toString())){
                            sameName = true;
                        }
                    }
                }
                if(sameName){
                    Toast.makeText(getApplicationContext(), "Options can not have the same name", Toast.LENGTH_LONG).show();
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
            });
    }

    public void saveOption(String optionName, String studyDuration, String breakDuration) {
        PomodoroSource pomodoro = SourceLocator.getInstance().getSource(PomodoroSource.class);
        pomodoro.putPreset(
                new PomodoroPreset(null, optionName, Integer.parseInt(studyDuration), Integer.parseInt(breakDuration)));
        finish();
        Intent back = new Intent(this, PomodoroOptionsActivity.class);
        startActivity(back);
    }
}