package com.merko.bilstudy;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class PreviousNotesActivity extends AppCompatActivity {
    private View card;
    private View back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_notes);
        card = findViewById(R.id.new_note);
        back = findViewById(R.id.backButton);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreviousNotesActivity.this, ChooseTemplateActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreviousNotesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}