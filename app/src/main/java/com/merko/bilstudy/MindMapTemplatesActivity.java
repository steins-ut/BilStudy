package com.merko.bilstudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MindMapTemplatesActivity extends AppCompatActivity {
    private View back;
    private View normalMM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mind_map_templates);
        back = findViewById(R.id.back_button);
        normalMM = findViewById(R.id.bubble_maps);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MindMapTemplatesActivity.this, ChooseTemplateActivity.class);
                startActivity(intent);
            }
        });
        normalMM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MindMapTemplatesActivity.this, MindMapActivity.class);
                startActivity(intent);
            }
        });
    }
}