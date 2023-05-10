package com.merko.bilstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class ChooseTemplateActivity extends AppCompatActivity {
    private View back;
    private View mindMaps;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_template);
        back = findViewById(R.id.back_button);
        mindMaps = findViewById(R.id.mind_maps);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseTemplateActivity.this, PreviousNotesActivity.class);
                startActivity(intent);
            }
        });
        mindMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseTemplateActivity.this, MindMapTemplatesActivity.class);
                startActivity(intent);
            }
        });

    }

}