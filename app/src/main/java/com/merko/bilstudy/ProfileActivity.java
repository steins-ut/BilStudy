package com.merko.bilstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity {
    Button buttonCal;
    Button buttonHist;
    Button buttonStat;
    Button buttonShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        buttonHist = findViewById(R.id.button);
        buttonHist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, StudyHistoryActivity.class);
                startActivity(intent);
            }
        });

        buttonStat = findViewById(R.id.button1);
        buttonStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,StudyStatisticsActivity.class);
                startActivity(intent);
            }
        });

        buttonCal = findViewById(R.id.button2);
        buttonCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,CalendarActivity.class);
                startActivity(intent);
            }
        });

        buttonShop = findViewById(R.id.button3);
        buttonShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,CalendarActivity.class);
                startActivity(intent);
            }
        });
    }
}