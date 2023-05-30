package com.merko.bilstudy;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.social.Profile;
import com.merko.bilstudy.social.ProfileSource;

import java.util.concurrent.ExecutionException;

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
        Profile p = null;
        try {
            p = SourceLocator.getInstance().getSource(ProfileSource.class).getLoggedInProfile().get();
            System.out.println("size of items: " + p.purchasedItems.size());
            for(int l = 0; l < p.purchasedItems.size(); l++){
                if(ShopActivity.shopItems != null){
                    for(int a = 0; a < ShopActivity.shopItems.size(); a++){
                        if(p.purchasedItems.get(l) == ShopActivity.shopItems.get(a).getUuid()){
                            System.out.println(ShopActivity.shopItems.get(a).getName());
                        }
                    }
                }
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
                    System.out.println("startcountdown called" + study + " study status");
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