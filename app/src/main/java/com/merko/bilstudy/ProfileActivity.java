package com.merko.bilstudy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {
    Button buttonCal;
    Button buttonHist;
    Button buttonStat;
    Button buttonShop;
    CardView backButton;
    TextView date;
    int coins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        date = findViewById(R.id.textView7);
        date.setText(currentDate);
        buttonHist = findViewById(R.id.buttonAdd);
        backButton = findViewById(R.id.backButtonPomodoro);

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
                Intent intent = new Intent(ProfileActivity.this,ShopActivity.class);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(getBaseContext(), MainActivity.class);
                startActivity(homepage);
            }
        });
    }
}