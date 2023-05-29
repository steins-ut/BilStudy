package com.merko.bilstudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudyStatisticsActivity extends AppCompatActivity {
    ArrayList<LeitnerStatistics> leitnerStatisticsArrayList = new ArrayList<>();
    String folder = "Biology Folder";
    String allCards = "All Cards: 100";
    String cardsStudied = "Cards Studied: 90";
    String accuracy = "Accuracy: %89";
    CardView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_statistics);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewStat);
        backButton = findViewById(R.id.backButtonStat);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(profile);
            }
        });

        setUpOldStudies();

        statisticsAdapter adapter = new statisticsAdapter(this,leitnerStatisticsArrayList);
        recyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
    }
    private void setUpOldStudies() {
        String[]studies = getResources().getStringArray(R.array.test_recycler);
        for(int i=0; i<10
                ; i++) {
            leitnerStatisticsArrayList.add(new LeitnerStatistics(folder,allCards,cardsStudied,accuracy));
        }
    }
}
