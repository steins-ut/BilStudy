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
import com.merko.bilstudy.utils.LeitnerUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class LeitnerQuestionTextActivity extends AppCompatActivity {

    private TextView questionNumberText;
    private TextView questionText;
    private Button checkButton;
    private ArrayList<LeitnerQuestion> questions;
    private int currQuestion = 0;
    private int previousChoice = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitner_text);

        SourceLocator locator = SourceLocator.getInstance();
        LeitnerSource source = locator.getSource(LeitnerSource.class);

        questions = new ArrayList<>();
        currQuestion = getIntent().getIntExtra("QUESTION_NUMBER", 0);
        questionNumberText  = findViewById(R.id.lnQuestionTextNumber);
        questionText = findViewById(R.id.lnQuestionTextText);
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
            if(previousChoice < 0) {
                return;
            }

            String msg;
            if(questions.get(currQuestion).choices.contains(previousChoice)) {
                msg = "Correct!";
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
            if(questions.get(currQuestion).type != LeitnerQuestionType.TEXT_ANSWER) {
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
        LeitnerQuestion question = questions.get(currQuestion);
        questionText.setText(question.question);
        questionNumberText.setText(getString(R.string.question_n, currQuestion + 1));
    }

}
