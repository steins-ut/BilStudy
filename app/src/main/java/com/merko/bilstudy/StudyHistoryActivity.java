package com.merko.bilstudy;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.social.OldStudies;
import com.merko.bilstudy.social.Profile;
import com.merko.bilstudy.social.ProfileSource;
import com.merko.bilstudy.ui.adapter.HistoryAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StudyHistoryActivity extends AppCompatActivity {
    ArrayList<OldStudies>oldStudiesArrayList = new ArrayList<>();
    int image= R.drawable.baseline_assignment_turned_in_24;
    Date date;
    CardView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_history);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewHistory);
        backButton = findViewById(R.id.backButtonCalendar);
        date = Calendar.getInstance().getTime();

        backButton.setOnClickListener(v -> {
            Intent profile = new Intent(getBaseContext(), ProfileActivity.class);
            startActivity(profile);
        });
        HistoryAdapter adapter = new HistoryAdapter(this,oldStudiesArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setUpOldStudies();
    }

    private void setUpOldStudies() {
        try {
            Profile p = SourceLocator.getInstance().getSource(ProfileSource.class).getLoggedInProfile().get();

            ArrayList<String>types= p.getTypesList();
            ArrayList<Integer>durations = p.getDurationsList();

            for(int i=0; i<types.size(); i++) {
                oldStudiesArrayList.add(new OldStudies(date.toString().substring(0,19),types.get(i),durations.get(i),image));
            }

            ProfileSource s = SourceLocator.getInstance().getSource(ProfileSource.class);
            s.updateProfile(p).join();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}