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
import com.merko.bilstudy.social.LeitnerQuestionStatistics;
import com.merko.bilstudy.social.Profile;
import com.merko.bilstudy.social.ProfileSource;
import com.merko.bilstudy.ui.adapter.LeitnerQuestionMCSAdapter;
import com.merko.bilstudy.utils.Globals;
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
    private Profile profile;
    private LeitnerQuestionStatistics questionStatistics;
    private LeitnerQuestion question;
    private int currQuestion;
    private int previousChoice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitner_multiple_choice_single);

        SourceLocator locator = SourceLocator.getInstance();
        LeitnerSource source = locator.getSource(LeitnerSource.class);
        ProfileSource profileSource = locator.getSource(ProfileSource.class);

        previousChoice = -1;
        questions = new ArrayList<>();
        currQuestion = getIntent().getIntExtra("QUESTION_NUMBER", 0) - 1;
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
            questionStatistics.solveDate = System.currentTimeMillis();
            if(questions.get(currQuestion).correctChoices.contains(previousChoice)) {
                msg = "Correct! +5 coins";
                profile.coin += 5;
                questionStatistics.solved = LeitnerQuestionStatistics.Solved.CORRECT;
                questionStatistics.correctCount = Math.max(1, questionStatistics.correctCount + 1);
                if(questionStatistics.correctCount >= Globals.LEITNER_FREQUENCY_CHANGE_COUNT) {
                    msg += "\nGot question wrong 3 times, moving to lower frequency";
                    questionStatistics.correctCount = 0;
                    questionStatistics.frequency = questionStatistics.frequency.getNextFrequency();
                }
            }
            else {
                msg = "False!";
                questionStatistics.solved = LeitnerQuestionStatistics.Solved.INCORRECT;
                questionStatistics.correctCount = Math.min(-1, questionStatistics.correctCount - 1);
                if(questionStatistics.correctCount <= -1 * Globals.LEITNER_FREQUENCY_CHANGE_COUNT) {
                    msg += "\nGot question wrong 3 times, moving to higher frequency";
                    questionStatistics.correctCount = 0;
                    questionStatistics.frequency = questionStatistics.frequency.getPreviousFrequency();
                }
            }
            profileSource.updateProfile(profile);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(msg)
                    .setPositiveButton("OK", (DialogInterface dialog, int id) -> nextQuestion());

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        nextQuestion();
    }

    private void nextQuestion() {
        SourceLocator locator = SourceLocator.getInstance();
        ProfileSource profileSource = locator.getSource(ProfileSource.class);

        profile = profileSource.getLoggedInProfile().join();
        currQuestion++;
        if(currQuestion >= questions.size()) {
            finish();
        }
        else {
            if(questions.get(currQuestion).type != LeitnerQuestionType.MULTIPLE_CHOICE_SINGLE) {
                Intent intent = LeitnerUtils.getQuestionIntent(this, questions.get(currQuestion).type);
                intent.putExtra("BOX_ID", getIntent().getStringExtra("BOX_ID"));
                intent.putExtra("QUESTION_NUMBER", currQuestion);
                startActivity(intent);
                finish();
            }
            else {
                question = questions.get(currQuestion);
                questionStatistics = profile.statistics
                        .leitnerStatistics.containerStatistics.get(question.containerId)
                        .questionStatistics.get(question.uuid);

                if(System.currentTimeMillis() - questionStatistics.solveDate < questionStatistics.frequency.getDayInterval() * Globals.DAY_TO_SECONDS) {
                    nextQuestion();
                }
                else {
                    previousChoice = -1;
                    setupUI();
                }
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
