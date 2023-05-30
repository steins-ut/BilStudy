package com.merko.bilstudy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.dialog.LoadingDialog;
import com.merko.bilstudy.leitner.LeitnerContainer;
import com.merko.bilstudy.leitner.LeitnerQuestion;
import com.merko.bilstudy.leitner.LeitnerSource;
import com.merko.bilstudy.ui.adapter.LeitnerQuestionAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class LeitnerBoxActivity extends AppCompatActivity {

    private LeitnerQuestionAdapter adapter;
    private List<LeitnerQuestion> questions;
    private UUID boxId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitner_box);

        boxId = UUID.fromString(getIntent().getStringExtra("CONTAINER_ID"));

        SourceLocator locator = SourceLocator.getInstance();
        LeitnerSource source = locator.getSource(LeitnerSource.class);

        RecyclerView questionRecycler = findViewById(R.id.lnBoxQuestionsRecycler);
        FloatingActionButton backButton = findViewById(R.id.lnBoxBackButton);
        FloatingActionButton playButton = findViewById(R.id.lnBoxPlayButton);
        FloatingActionButton editButton = findViewById(R.id.lnBoxEditButton);
        TextView boxName = findViewById(R.id.lnBoxName);
        TextView boxTags = findViewById(R.id.lnBoxTags);
        TextView boxQuestionCount = findViewById(R.id.lnBoxQuestions);
        Button allButton = findViewById(R.id.lnBoxAll);
        Button weeklyButton =findViewById(R.id.lnBoxWeekly);
        Button thridailyButton = findViewById(R.id.lnBoxThridaily);
        Button dailyButton =findViewById(R.id.lnBoxDaily);

        LoadingDialog dialog = new LoadingDialog(this);
        CompletableFuture<LeitnerContainer> future = source.getContainer(boxId);
        dialog.addFutures(future);
        dialog.show();

        LeitnerContainer box = future.join();
        boxName.setText(box.name);
        StringBuilder tags = new StringBuilder();
        for(String t: box.tags) {
            tags.append("#").append(t).append(" ");
        }
        boxTags.setText(tags.toString());
        boxQuestionCount.setText(getString(R.string.n_questions, box.objectIds.size()));

        LoadingDialog dialog2 = new LoadingDialog(this);
        CompletableFuture<LeitnerQuestion[]> future2 = source.getQuestions(box);
        dialog2.addFutures(future2);
        dialog2.show();

        questions = Arrays.asList(future2.join());
        adapter = new LeitnerQuestionAdapter(questions);

        questionRecycler.setAdapter(adapter);
        questionRecycler.setLayoutManager(new LinearLayoutManager(this));

        backButton.setOnClickListener((View view) -> finish());

        backButton.show();
        playButton.show();
        editButton.show();
    }
}
