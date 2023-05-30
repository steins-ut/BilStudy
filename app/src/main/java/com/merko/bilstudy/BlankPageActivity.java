package com.merko.bilstudy;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class BlankPageActivity extends AppCompatActivity {
    private View back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_page);

        back = findViewById(R.id.back_button);

    }
}