package com.merko.bilstudy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.dialog.LoadingDialog;
import com.merko.bilstudy.leitner.LeitnerContainer;
import com.merko.bilstudy.leitner.LeitnerQuestion;
import com.merko.bilstudy.leitner.LeitnerQuestionType;
import com.merko.bilstudy.leitner.LeitnerSource;
import com.merko.bilstudy.social.LeitnerQuestionStatistics;
import com.merko.bilstudy.social.Profile;
import com.merko.bilstudy.social.ProfileSource;
import com.merko.bilstudy.utils.Globals;
import com.merko.bilstudy.utils.LeitnerUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class LeitnerQuestionTextActivity extends AppCompatActivity {

    private TextView questionNumberText;
    private TextView questionText;
    private TextView answerText;
    private Button checkButton;
    private ArrayList<LeitnerQuestion> questions;
    private Profile profile;
    private LeitnerQuestionStatistics questionStatistics;
    private LeitnerQuestion question;
    private int currQuestion = 0;
    private int previousChoice = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitner_text);

        SourceLocator locator = SourceLocator.getInstance();
        LeitnerSource source = locator.getSource(LeitnerSource.class);
        ProfileSource profileSource = locator.getSource(ProfileSource.class);

        questions = new ArrayList<>();
        currQuestion = getIntent().getIntExtra("QUESTION_NUMBER", 0);
        questionNumberText  = findViewById(R.id.lnQuestionTextNumber);
        questionText = findViewById(R.id.lnQuestionTextText);
        answerText = findViewById(R.id.lnQuestionTextAnswer);
        checkButton = findViewById(R.id.lnQuestionTextCheckAnswer);

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

        checkButton.setOnClickListener((View view) -> {
            if(answerText.getText().toString().isEmpty()) {
                return;
            }

            String msg;
            questionStatistics.solveDate = System.currentTimeMillis();
            if(questions.get(currQuestion).choices.contains(answerText.getText().toString())) {
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
            if(questions.get(currQuestion).type != LeitnerQuestionType.TEXT_ANSWER) {
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
        LeitnerQuestion question = questions.get(currQuestion);
        questionText.setText(question.question);
        questionNumberText.setText(getString(R.string.question_n, currQuestion + 1));
        answerText.setText("");
    }

}
