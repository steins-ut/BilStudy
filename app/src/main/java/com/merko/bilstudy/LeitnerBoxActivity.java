package com.merko.bilstudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.dialog.LeitnerQuestionAddDialog;
import com.merko.bilstudy.dialog.LoadingDialog;
import com.merko.bilstudy.leitner.LeitnerContainer;
import com.merko.bilstudy.leitner.LeitnerQuestion;
import com.merko.bilstudy.leitner.LeitnerQuestionType;
import com.merko.bilstudy.leitner.LeitnerSource;
import com.merko.bilstudy.ui.adapter.LeitnerQuestionAdapter;
import com.merko.bilstudy.ui.holder.LeitnerQuestionHolder;
import com.merko.bilstudy.utils.LeitnerUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class LeitnerBoxActivity extends AppCompatActivity implements LeitnerQuestionHolder.ClickListener {

    private RecyclerView questionRecycler;
    private FloatingActionButton backButton;
    private FloatingActionButton playButton;
    private FloatingActionButton addButton;
    private FloatingActionButton saveButton;
    private FloatingActionButton editButton;
    private TextView boxName;
    private TextView boxTags;
    private TextView boxQuestionCount;
    private Button allButton;
    private Button weeklyButton;
    private Button thridailyButton;
    private Button dailyButton;
    private LeitnerQuestionAdapter adapter;
    private List<LeitnerQuestion> questions;
    private LeitnerContainer box;
    private boolean editing = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitner_box);

        SourceLocator locator = SourceLocator.getInstance();
        LeitnerSource source = locator.getSource(LeitnerSource.class);

        questionRecycler = findViewById(R.id.lnBoxQuestionsRecycler);
        backButton = findViewById(R.id.lnBoxBackButton);
        playButton = findViewById(R.id.lnBoxPlayButton);
        addButton = findViewById(R.id.lnBoxAddButton);
        saveButton = findViewById(R.id.lnBoxSaveButton);
        editButton = findViewById(R.id.lnBoxEditButton);
        boxName = findViewById(R.id.lnBoxName);
        boxTags = findViewById(R.id.lnBoxTags);
        boxQuestionCount = findViewById(R.id.lnBoxQuestions);
        allButton = findViewById(R.id.lnBoxAll);
        weeklyButton =findViewById(R.id.lnBoxWeekly);
        thridailyButton = findViewById(R.id.lnBoxThridaily);
        dailyButton = findViewById(R.id.lnBoxDaily);

        adapter = new LeitnerQuestionAdapter(null, this);
        questionRecycler.setAdapter(adapter);
        questionRecycler.setLayoutManager(new LinearLayoutManager(this));

        reloadBox();

        backButton.setOnClickListener((View view) -> finish());
        playButton.setOnClickListener((View view) -> {
            if(questions.size() < 1) {
                return;
            }
            Intent intent = LeitnerUtils.getQuestionIntent(this, questions.get(0).type);
            intent.putExtra("BOX_ID", box.uuid.toString());
            startActivity(intent);
        });

        editButton.setOnClickListener((View view) -> {
            editing = true;

            editButton.hide();
            playButton.hide();
            saveButton.show();
            addButton.show();
        });

        saveButton.setOnClickListener((View view) -> {
            editing = false;

            editButton.show();
            playButton.show();
            saveButton.hide();
            addButton.hide();
        });

        addButton.setOnClickListener((View view) -> {
            LeitnerQuestionAddDialog addDialog = new LeitnerQuestionAddDialog(this);
            addDialog.setOnClickListener((LeitnerQuestionType type) -> {
                Intent intent = LeitnerUtils.getAddQuestionIntent(this, type);
                intent.putExtra("QUESTION_NUMBER", questions.size());
                intent.putExtra("BOX_ID", box.uuid.toString());
                startActivity(intent);
            });
            addDialog.show();
        });

        saveButton.hide();
        addButton.hide();
        backButton.show();
        playButton.show();
        editButton.show();
    }

    private void reloadBox() {
        UUID boxId = UUID.fromString(getIntent().getStringExtra("CONTAINER_ID"));
        SourceLocator locator = SourceLocator.getInstance();
        LeitnerSource source = locator.getSource(LeitnerSource.class);

        LoadingDialog dialog = new LoadingDialog(this);
        CompletableFuture<LeitnerContainer> future = source.getContainer(boxId);
        dialog.addFutures(future);
        dialog.show();

        box = future.join();
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
        questions = new ArrayList<>(Arrays.asList(future2.join()));
        adapter.setQuestions(questions);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadBox();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        reloadBox();
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public boolean onItemLongClick(int position) {
        if(editing) {
            SourceLocator locator = SourceLocator.getInstance();
            LeitnerSource source = locator.getSource(LeitnerSource.class);
            RecyclerView.ViewHolder holder =  questionRecycler.findViewHolderForAdapterPosition(position);
            if(holder == null) {
                return false;
            }

            PopupMenu menu = new PopupMenu(this, holder.itemView);
            menu.setOnMenuItemClickListener((MenuItem item) -> {
                LeitnerQuestion question = questions.get(position);
                questions.remove(position);
                source.deleteQuestion(question.uuid);
                adapter.notifyDataSetChanged();
                return true;
            });

            menu.inflate(R.menu.popup_leitner_question);
            menu.show();
            return true;
        }
        return false;
    }
}
