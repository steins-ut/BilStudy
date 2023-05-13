package com.merko.bilstudy;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    Button language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        language = findViewById(R.id.button5);
        language.setOnClickListener(view -> selectLanguage());
    }
    public void selectLanguage() {
        AlertDialog.Builder select = new AlertDialog.Builder(this);
        select.setMessage("Select language");
        select.create();
    }
}