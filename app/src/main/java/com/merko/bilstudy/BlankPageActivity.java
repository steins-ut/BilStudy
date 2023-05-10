package com.merko.bilstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class BlankPageActivity extends AppCompatActivity {
    private View back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_page);

        back = findViewById(R.id.back_button);

    }
}