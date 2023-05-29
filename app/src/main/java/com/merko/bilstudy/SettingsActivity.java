package com.merko.bilstudy;

import android.content.DialogInterface;
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
    Button notificationsOn;
    Button notificationsOff;
    Button selectTime;
    CardView backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        playSound = findViewById(R.id.buttonPlay);
        stopSound = findViewById(R.id.buttonStop);
        backButton = findViewById(R.id.backButtonPomodoro);
        notificationsOff = findViewById(R.id.buttonOFF);
        notificationsOn = findViewById(R.id.buttonOn);
        selectTime = findViewById(R.id.buttonSelectTime);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(getBaseContext(), MainActivity.class);
                startActivity(homepage);
            }
        });
        playSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(SettingsActivity.this);
                alert.setTitle("Background Sound");
                alert.setMessage("Which sound do you want to study with?");
                alert.setCancelable(false);
                alert.setNegativeButton("Rain", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
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
                    }
                });
                alert.setPositiveButton("LoFi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            play2.stop();
                            play2.release();
                        }
                        catch(Exception e) {
                            //null
                        }
                        try {
                            play2.reset();
                        }
                        catch(Exception e) {
                            //null
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

                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });
        stopSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(play);
                finish(play2);
            }
        });
        notificationsOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changeNotification(false);
            }
        });
        notificationsOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changeNotification(true);
            }
        });
        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(getBaseContext(), MainActivity.class);
                startActivity(homepage);
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
    //public void changeNotification(Boolean booleanVal) {
        //MainActivity.notifications = booleanVal;
    //}

}