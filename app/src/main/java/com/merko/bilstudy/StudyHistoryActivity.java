package com.merko.bilstudy;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.social.OldStudies;
import com.merko.bilstudy.ui.adapter.HistoryAdapter;

import java.util.ArrayList;

public class StudyHistoryActivity extends AppCompatActivity {
    ArrayList<OldStudies>oldStudiesArrayList = new ArrayList<>();
    int image= R.drawable.baseline_assignment_turned_in_24;
    String date = "20.05.2023";

    CardView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_history);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewHistory);
        backButton = findViewById(R.id.backButtonCalendar);

        backButton.setOnClickListener(v -> {
            Intent profile = new Intent(getBaseContext(), ProfileActivity.class);
            startActivity(profile);
        });

        setUpOldStudies();

        HistoryAdapter adapter = new HistoryAdapter(this,oldStudiesArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void setUpOldStudies() {
        String[]studies = getResources().getStringArray(R.array.test_recycler);
        for(int i=0; i< studies.length; i++) {
            oldStudiesArrayList.add(new OldStudies(date,studies[i], image));
        }
    }
}