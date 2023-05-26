package com.merko.bilstudy;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    MediaPlayer play;
    MediaPlayer play2;
    Button playSound;
    Button stopSound;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        playSound = findViewById(R.id.buttonPlay);
        stopSound = findViewById(R.id.buttonStop);
        play = MediaPlayer.create(SettingsActivity.this,R.raw.rain);
        play2 = MediaPlayer.create(SettingsActivity.this,R.raw.lofi);
        back = findViewById(R.id.backButtonSettings);
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
                        play.start();
                    }
                });
                alert.setPositiveButton("LoFi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        play2.start();

                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });
        stopSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play.stop();
                play2.stop();
            }
        });


    }

}