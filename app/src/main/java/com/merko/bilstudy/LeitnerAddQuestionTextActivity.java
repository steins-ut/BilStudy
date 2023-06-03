package com.merko.bilstudy;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.dialog.LoadingDialog;
import com.merko.bilstudy.leitner.LeitnerQuestion;
import com.merko.bilstudy.leitner.LeitnerQuestionType;
import com.merko.bilstudy.leitner.LeitnerSource;

import java.util.ArrayList;
import java.util.UUID;

public class LeitnerAddQuestionTextActivity extends AppCompatActivity {

    private TextView questionNumberText;
    private EditText questionText;
    private EditText answerText;
    private FloatingActionButton deleteButton;
    private FloatingActionButton saveButton;
    private int currQuestion = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitner_add_text);

        SourceLocator locator = SourceLocator.getInstance();
        LeitnerSource source = locator.getSource(LeitnerSource.class);

        currQuestion = getIntent().getIntExtra("QUESTION_NUMBER", 0);
        questionNumberText  = findViewById(R.id.lnAddQuestionTextNumber);
        questionText = findViewById(R.id.lnAddQuestionTextQuestion);
        deleteButton = findViewById(R.id.lnAddQuestionTextDelete);
        saveButton = findViewById(R.id.lnAddQuestionTextSave);
        answerText = findViewById(R.id.lnAddQuestionTextAnswer);

        questionNumberText.setText(getString(R.string.question_n, currQuestion + 1));

        deleteButton.setOnClickListener((View view) -> {
            finish();
        });

        saveButton.setOnClickListener((View view) -> {
            if(answerText.getText().toString().isEmpty()) {
                return;
            }
            LeitnerQuestion question = new LeitnerQuestion();
            question.question = questionText.getText().toString();
            question.type = LeitnerQuestionType.TEXT_ANSWER;
            question.containerId = UUID.fromString(getIntent().getStringExtra("BOX_ID"));
            question.choices = new ArrayList<>();
            question.choices.add(answerText.getText().toString());
            question.correctChoices = new ArrayList<>();
            LoadingDialog dialog = new LoadingDialog(this);
            dialog.addFutures(source.putQuestion(question));
            dialog.show();
            finish();
        });
    }
}
