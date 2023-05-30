package com.merko.bilstudy;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.social.LeitnerStatistics;
import com.merko.bilstudy.social.Profile;
import com.merko.bilstudy.social.ProfileSource;
import com.merko.bilstudy.ui.adapter.StatisticsAdapter;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class StudyStatisticsActivity extends AppCompatActivity {
    ArrayList<LeitnerStatistics> leitnerStatisticsArrayList = new ArrayList<>();
    CardView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_statistics);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewStat);
        backButton = findViewById(R.id.backButtonStat);
        backButton.setOnClickListener(v -> {
            Intent profile = new Intent(getBaseContext(), ProfileActivity.class);
            startActivity(profile);
        });
        StatisticsAdapter adapter = new StatisticsAdapter(this,leitnerStatisticsArrayList);
        recyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        setUpOldStudies();
    }

    private void setUpOldStudies() {
        try {
            Profile p = SourceLocator.getInstance().getSource(ProfileSource.class).getLoggedInProfile().get();

            ArrayList<String>folderList = p.getFolderNamesList();
            ArrayList<Integer>allCardsList = p.getAllCardsList();
            ArrayList<Integer>cardsStudiedList = p.getCardsStudiedList();
            ArrayList<Integer>accuracy = p.getAccuracyList();

            for(int i=0; i<folderList.size(); i++) {
                leitnerStatisticsArrayList.add(new LeitnerStatistics(folderList.get(i),allCardsList.get(i),cardsStudiedList.get(i),accuracy.get(i)));
            }

            ProfileSource s = SourceLocator.getInstance().getSource(ProfileSource.class);
            s.updateProfile(p).join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
