package com.merko.bilstudy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QuestionnaireActivity extends AppCompatActivity {

    CardView backButton;
    Button yes1;
    Button no1;
    Button yes2;
    Button no2;
    Button yes3;
    Button no3;
    Button yes4;
    Button no4;
    Button yes5;
    Button no5;
    CardView seeResults;

    static boolean y1;
    static boolean n1;
    static boolean y2;
    static boolean n2;
    static boolean y3;
    static boolean n3;
    static boolean y4;
    static boolean n4;
    static boolean y5;
    static boolean n5;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_screen);

        backButton = findViewById(R.id.backButtonPomodoro);
        yes1 = findViewById(R.id.yes1);
        no1 = findViewById(R.id.no1);
        yes2 = findViewById(R.id.yes2);
        no2 = findViewById(R.id.no2);
        yes3 = findViewById(R.id.yes3);
        no3 = findViewById(R.id.no3);
        yes4 = findViewById(R.id.yes4);
        no4 = findViewById(R.id.no4);
        yes5 = findViewById(R.id.yes5);
        no5 = findViewById(R.id.no5);
        seeResults = findViewById(R.id.seeResults);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(getBaseContext(), MainActivity.class);
                startActivity(homepage);
            }
        });

        yes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                y1 = true;
                n1 = false;
            }
        });
        yes2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                y2 = true;
                n2 = false;
            }
        });
        yes3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                y3 = true;
                n3 = false;
            }
        });
        yes4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                y4 = true;
                n4 = false;
            }
        });
        yes5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                y5 = true;
                n5 = false;
            }
        });
        no1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n1 = true;
                y1 = false;
            }
        });
        no2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n2 = true;
                y2 = false;
            }
        });
        no3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n3 = true;
                y3 = false;
            }
        });
        no4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n4 = true;
                y4 = false;
            }
        });
        no5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n5 = true;
                y5 = false;
            }
        });
        seeResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultPage = new Intent(getBaseContext(), QuestionnaireResultsActivity.class);
                startActivity(resultPage);
            }
        });

    }

}