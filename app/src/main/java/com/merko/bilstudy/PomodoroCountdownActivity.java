package com.merko.bilstudy;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PomodoroCountdownActivity extends AppCompatActivity {

    public long studyDuration;
    public long breakDuration;

    TextView countdownText;
    TextView motivationText;

    Button startPauseButton;
    CardView backButton;
    CountDownTimer timer;
    boolean timerActive = false;
    long startTime;
    long timeLeft;
    boolean study = false;
    boolean breakk = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        breakDuration = PomodoroViewHolder.getBreakMins() * 60000;
        studyDuration = PomodoroViewHolder.getStudyMins() * 60000;
        startTime = studyDuration;
        timeLeft = startTime;
        study = true;
        breakk = false;
        setContentView(R.layout.activity_pomodoro_countdown);
        countdownText = findViewById(R.id.countDownText);
        startPauseButton = findViewById(R.id.startPauseButton);
        motivationText = findViewById(R.id.motivationText);
        backButton = findViewById(R.id.backButtonPomodoro);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent options = new Intent(getBaseContext(), PomodoroOptionsActivity.class);
                startActivity(options);
            }
        });

        startPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerActive){
                    pauseCountdown();
                }
                else {
                    startCountdown();
                }
            }
        });
        updateCountdownText();
    }
    public void startCountdown(){
        timer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                if(study){
                    timer.cancel();
                    study = false;
                    breakk = true;
                    timeLeft = breakDuration;
                    motivationText.setText("Break time! Have some rest!");
                    startCountdown();
                }
                else if(breakk){
                    timer.cancel();
                    breakk = false;
                    study = true;
                    timeLeft = studyDuration;
                    motivationText.setText("Study time! Keep going, you're doing great!");
                    startCountdown();
                }
            }
        }.start();
        timerActive = true;
        startPauseButton.setText("PAUSE");
    }

    public void updateCountdownText(){
        int min = (int) (timeLeft / 1000) / 60;
        int sec = (int) (timeLeft / 1000) % 60;

        String countdown = String.format("%02d:%02d", min, sec);

        countdownText.setText(countdown);
    }

    public void pauseCountdown(){
        timer.cancel();
        timerActive = false;
        startPauseButton.setText("START");
    }

}