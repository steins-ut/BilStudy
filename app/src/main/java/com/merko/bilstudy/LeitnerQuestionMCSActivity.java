package com.merko.bilstudy;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.dialog.LoadingDialog;
import com.merko.bilstudy.leitner.LeitnerContainer;
import com.merko.bilstudy.leitner.LeitnerQuestion;
import com.merko.bilstudy.leitner.LeitnerQuestionType;
import com.merko.bilstudy.leitner.LeitnerSource;
import com.merko.bilstudy.social.ProfileSource;
import com.merko.bilstudy.ui.adapter.LeitnerQuestionMCSAdapter;
import com.merko.bilstudy.utils.LeitnerUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class LeitnerQuestionMCSActivity extends AppCompatActivity {

    private TextView questionNumberText;
    private TextView questionText;
    private RecyclerView choiceRecycler ;
    private Button checkButton;
    private LeitnerQuestionMCSAdapter adapter;
    private ArrayList<LeitnerQuestion> questions;
    private int currQuestion = 0;
    private int previousChoice = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitner_multiple_choice_single);

        SourceLocator locator = SourceLocator.getInstance();
        LeitnerSource source = locator.getSource(LeitnerSource.class);
        ProfileSource profileSource = locator.getSource(ProfileSource.class);

        questions = new ArrayList<>();
        currQuestion = getIntent().getIntExtra("QUESTION_NUMBER", 0);
        questionNumberText  = findViewById(R.id.lnQuestionMCSNumber);
        questionText = findViewById(R.id.lnQuestionMCSText);
        choiceRecycler = findViewById(R.id.lnQuestionMCSChoices);
        checkButton = findViewById(R.id.lnQuestionMCSCheckAnswer);

        if(getIntent().hasExtra("BOX_ID")) {
            UUID boxId = UUID.fromString(getIntent().getStringExtra("BOX_ID"));
            LoadingDialog containerDialog = new LoadingDialog(this);
            CompletableFuture<LeitnerContainer> containerFuture = source.getContainer(boxId);
            containerDialog.addFutures(containerFuture);
            containerDialog.show();

            LoadingDialog questionsDialog = new LoadingDialog(this);
            CompletableFuture<LeitnerQuestion[]> questionFuture = source.getQuestions(containerFuture.join());
            questionsDialog.addFutures(questionFuture);
            questionsDialog.show();

            questions.addAll(Arrays.asList(questionFuture.join()));
        }
        else {
            UUID questionId = UUID.fromString(getIntent().getStringExtra("QUESTION_ID"));
            LoadingDialog questionDialog = new LoadingDialog(this);
            CompletableFuture<LeitnerQuestion> questionFuture = source.getQuestion(questionId);
            questionDialog.addFutures(questionFuture);
            questionDialog.show();

            questions.add(questionFuture.join());
        }

        adapter = new LeitnerQuestionMCSAdapter(null, (int position) -> {
            if(previousChoice != -1) {
                choiceRecycler.findViewHolderForAdapterPosition(previousChoice).itemView
                        .setForeground(new ColorDrawable(Color.argb(0, 0, 0, 0)));
            }
            choiceRecycler.findViewHolderForAdapterPosition(position).itemView
                    .setForeground(new ColorDrawable(Color.argb(0x3f, 0, 0, 0)));
            previousChoice = position;
        });

        choiceRecycler.setAdapter(adapter);
        choiceRecycler.setLayoutManager(new LinearLayoutManager(this));

        checkButton.setOnClickListener((View view) -> {
            if(previousChoice < 0) {
                return;
            }

            String msg;
            if(questions.get(currQuestion).correctChoices.contains(previousChoice)) {
                msg = "Correct! +5 coins";
                profileSource.getLoggedInProfile().join().coin += 5;
                profileSource.updateProfile(profileSource.getLoggedInProfile().join());
            }
            else {
                msg = "False!";
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(msg)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            nextQuestion();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        setupUI();
    }

    private void nextQuestion() {
        currQuestion++;
        if(currQuestion >= questions.size()) {
            finish();
        }
        else {
            if(questions.get(currQuestion).type != LeitnerQuestionType.MULTIPLE_CHOICE_SINGLE) {
                Intent intent = LeitnerUtils.getQuestionIntent(this, questions.get(currQuestion).type);
                intent.putExtra("BOX_ID", getIntent().getStringExtra("BOX_ID"));
                startActivity(intent);
                finish();
            }
            else {
                previousChoice = -1;
                setupUI();
            }
        }
    }

    private void setupUI() {
        for(int i  = 0; i < choiceRecycler.getChildCount(); i++) {
            choiceRecycler.findViewHolderForAdapterPosition(i).itemView
                    .setForeground(new ColorDrawable(Color.argb(0, 0, 0, 0)));
        }
        LeitnerQuestion question = questions.get(currQuestion);
        questionText.setText(question.question);
        questionNumberText.setText(getString(R.string.question_n, currQuestion + 1));
        adapter.setChoices(question.choices);
    }
}
