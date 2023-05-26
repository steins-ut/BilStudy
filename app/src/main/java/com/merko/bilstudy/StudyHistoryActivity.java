package com.merko.bilstudy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudyHistoryActivity extends AppCompatActivity {
    ArrayList<OldStudies>oldStudiesArrayList = new ArrayList<>();
    int image= R.drawable.baseline_assignment_turned_in_24;
    String date = "20.05.2023";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_history);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewHistory);

        setUpOldStudies();

        historyAdapter adapter = new historyAdapter(this,oldStudiesArrayList);
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