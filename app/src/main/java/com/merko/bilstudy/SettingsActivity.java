package com.merko.bilstudy;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class SettingsActivity extends AppCompatActivity {
    MediaPlayer play;
    MediaPlayer play2;
    Button playSound;
    Button stopSound;
    CardView backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        playSound = findViewById(R.id.buttonPlay);
        stopSound = findViewById(R.id.buttonStop);
        backButton = findViewById(R.id.backButtonPomodoro);

        backButton.setOnClickListener(v -> {
            Intent homepage = new Intent(getBaseContext(), MainActivity.class);
            startActivity(homepage);
        });
        playSound.setOnClickListener(view -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(SettingsActivity.this);
            alert.setTitle("Background Sound");
            alert.setMessage("Which sound do you want to study with?");
            alert.setCancelable(false);
            alert.setNegativeButton("Rain", (dialogInterface, i) -> {
                try {
                    play.stop();
                    play.release();
                }
                catch(Exception e) {
                    //null
                }
                try {
                    play.reset();
                }
                catch(Exception e) {
                    //null
                }
                play = MediaPlayer.create(SettingsActivity.this,R.raw.rain);
                try {
                    play.prepare();
                }
                catch(Exception e){
                }
                try {
                    play.start();
                }
                catch (IllegalStateException e) {

                }
            });
            alert.setPositiveButton("LoFi", (dialogInterface, i) -> {
                try {
                    play2.stop();
                    play2.release();
                }
                catch(Exception e) {
                }
                try {
                    play2.reset();
                }
                catch(Exception e) {
                }
                play2 = MediaPlayer.create(SettingsActivity.this,R.raw.lofi);
                try {
                    play2.prepare();
                }
                catch(Exception e){
                }
                try {
                    play2.start();
                }
                catch (IllegalStateException e) {
                }

            });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        });
        stopSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(play);
                finish(play2);
            }
        });
    }
    public void finish(MediaPlayer player) {
        try {
            player.release();
        }
        catch(Exception e) {
        }
    }
}
