package com.merko.bilstudy;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.dialog.LoadingDialog;
import com.merko.bilstudy.leitner.LeitnerQuestion;
import com.merko.bilstudy.leitner.LeitnerQuestionType;
import com.merko.bilstudy.leitner.LeitnerSource;
import com.merko.bilstudy.ui.adapter.LeitnerQuestionMCSAdapter;

import java.util.ArrayList;
import java.util.UUID;

public class LeitnerAddQuestionMCSActivity extends AppCompatActivity {

    private TextView questionNumberText;
    private TextView questionText;
    private CardView addChoiceCard;
    private RecyclerView choiceRecycler ;
    private FloatingActionButton deleteButton;
    private FloatingActionButton saveButton;
    private LeitnerQuestionMCSAdapter adapter;
    private int currQuestion = 0;
    private int previousChoice = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitner_add_multiple_choice_single);

        SourceLocator locator = SourceLocator.getInstance();
        LeitnerSource source = locator.getSource(LeitnerSource.class);

        currQuestion = getIntent().getIntExtra("QUESTION_NUMBER", 0);
        addChoiceCard = findViewById(R.id.lnAddQuestionAddChoice);
        questionNumberText  = findViewById(R.id.lnAddQuestionMCSNumber);
        questionText = findViewById(R.id.lnAddQuestionMCSQuestion);
        choiceRecycler = findViewById(R.id.lnAddQuestionMCSChoices);
        deleteButton = findViewById(R.id.lnAddQuestionMCSDelete);
        saveButton = findViewById(R.id.lnAddQuestionMCSSave);

        questionNumberText.setText(getString(R.string.question_n, currQuestion + 1));

        addChoiceCard.setOnClickListener((View view) -> {
            adapter.getChoices().add("");
            adapter.setChoices(adapter.getChoices());
        });

        adapter = new LeitnerQuestionMCSAdapter(new ArrayList<>(), (int position) -> {
            if(previousChoice != -1) {
                choiceRecycler.findViewHolderForAdapterPosition(previousChoice).itemView
                        .setForeground(new ColorDrawable(Color.argb(0, 0, 0, 0)));
            }
            if(previousChoice != adapter.getItemCount() - 1) {
                choiceRecycler.findViewHolderForAdapterPosition(position).itemView
                        .setForeground(new ColorDrawable(Color.argb(0x3f, 0, 0, 0)));
                previousChoice = position;
            }
        }, true);

        choiceRecycler.setAdapter(adapter);
        choiceRecycler.setLayoutManager(new LinearLayoutManager(this));

        deleteButton.setOnClickListener((View view) -> {
            finish();
        });

        saveButton.setOnClickListener((View view) -> {
            ArrayList<String> choices =  new ArrayList<>();
            for(int i = 0; i < adapter.getItemCount(); i++) {
                RecyclerView.ViewHolder viewHolder = choiceRecycler.findViewHolderForAdapterPosition(i);
                if(viewHolder != null) {
                    TextView textView = viewHolder.itemView.findViewById(R.id.lnQuestionChoiceEntryChoice);

                    choices.add(textView.getText().toString());
                }
            }
            if(choices.size() == 0) {
                return;
            }
            if(previousChoice == -1) {
                return;
            }
            LeitnerQuestion question = new LeitnerQuestion();
            question.question = questionText.getText().toString();
            question.type = LeitnerQuestionType.MULTIPLE_CHOICE_SINGLE;
            question.containerId = UUID.fromString(getIntent().getStringExtra("BOX_ID"));
            question.choices = choices;
            question.correctChoices = new ArrayList<Integer>();
            question.correctChoices.add(previousChoice);
            LoadingDialog dialog = new LoadingDialog(this);
            dialog.addFutures(source.putQuestion(question));
            dialog.show();
            finish();
        });
    }
}
