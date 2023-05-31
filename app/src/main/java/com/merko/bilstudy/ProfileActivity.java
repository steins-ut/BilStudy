package com.merko.bilstudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.social.Profile;
import com.merko.bilstudy.social.ProfileSource;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity {
    Button buttonCal;
    Button buttonHist;
    Button buttonStat;
    Button buttonShop;
    CardView backButton;
    CardView home;
    CardView settings;
    TextView date;
    TextView coinText;
    int coins;
    static ImageView lamp;
    static ImageView mug;
    static ImageView pens;


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
        settings = findViewById(R.id.settingBut);
        coinText = findViewById(R.id.coinsText);
        mug = findViewById(R.id.mug);
        lamp = findViewById(R.id.lamp);
        pens = findViewById(R.id.pen);
        try {
            Profile p = SourceLocator.getInstance().getSource(ProfileSource.class).getLoggedInProfile().get();
            coinText.setText("Coins: " + p.coin);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        buttonHist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, StudyHistoryActivity.class);
                startActivity(intent);
            }
        });

        //buttonStat = findViewById(R.id.button1);
        /*
        * buttonStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,StudyStatisticsActivity.class);
                startActivity(intent);
            }
        });*/

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
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homepage = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(homepage);
            }
        });


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        mug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, PomodoroOptionsActivity.class);
                startActivity(intent);
            }
        });
        pens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ChooseTemplateActivity.class);
                startActivity(intent);
            }
        });
        lamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, LeitnerHomeActivity.class);
                startActivity(intent);
            }
        });
    }

}