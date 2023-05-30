package com.merko.bilstudy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class QuestionnaireResultsActivity extends AppCompatActivity {
    String adviseText = "";
    CardView advise;
    TextView adviseTextView;
    CardView backResults;
    TextView[] advises;
    CardView[] adviseButtons;
    CardView advise1;
    CardView advise2;
    CardView advise3;
    CardView advise4;
    CardView advise5;
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    TextView text5;
    int currentAdviseNo = 0;
    boolean leitner = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_results);
        advise = findViewById(R.id.cardViewAdvise);
        adviseTextView = findViewById(R.id.allAdvises);
        backResults = findViewById(R.id.backQuestionnaireResults);
        advise1 = findViewById(R.id.advise1);
        advise2 = findViewById(R.id.advise2);
        advise3 = findViewById(R.id.advise3);
        advise4 = findViewById(R.id.advise4);
        advise5 = findViewById(R.id.advise5);
        text1 = findViewById(R.id.adviseText1);
        text2 = findViewById(R.id.adviseText2);
        text3 = findViewById(R.id.adviseText3);
        text4 = findViewById(R.id.adviseText4);
        text5 = findViewById(R.id.adviseText5);
        advises = new TextView[]{text1, text2, text3, text4, text5};
        adviseButtons = new CardView[]{advise1, advise2, advise3, advise4, advise5};
        if(QuestionnaireActivity.y1){
            adviseText += "-Since you are a visual learner, we advise you to study with our mind map templates in our notepad section.\n\n";
            advises[currentAdviseNo].setText("Go to mind maps now");
            advises[currentAdviseNo].setTextSize(16);
            advises[currentAdviseNo].setTextColor(Color.BLACK);
            adviseButtons[currentAdviseNo].setVisibility(View.VISIBLE);
            adviseButtons[currentAdviseNo].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mindMap = new Intent(QuestionnaireResultsActivity.this, MindMapActivity.class);
                    startActivity(mindMap);
                }
            });
            currentAdviseNo++;

        }
        if(QuestionnaireActivity.y2){
            adviseText += "-Since you are having a hard time staying focused for long period of times, we advise you to use our 'Classic Pomodoro' option to study with.\n\n";
            advises[currentAdviseNo].setText("Go to pomodoro now");
            advises[currentAdviseNo].setTextSize(16);
            advises[currentAdviseNo].setTextColor(Color.BLACK);
            adviseButtons[currentAdviseNo].setVisibility(View.VISIBLE);
            adviseButtons[currentAdviseNo].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pomodoro = new Intent(QuestionnaireResultsActivity.this, PomodoroOptionsActivity.class);
                    startActivity(pomodoro);
                }
            });
            currentAdviseNo++;
        }
        if(QuestionnaireActivity.n2){
            adviseText += "-Since you are not having a hard time staying focused for long period of times, we advise you to use our 'Extreme Pomodoro' option to study with.\n\n";
            advises[currentAdviseNo].setText("Go to pomodoro now");
            advises[currentAdviseNo].setTextSize(16);
            advises[currentAdviseNo].setTextColor(Color.BLACK);
            adviseButtons[currentAdviseNo].setVisibility(View.VISIBLE);
            adviseButtons[currentAdviseNo].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pomodoro = new Intent(QuestionnaireResultsActivity.this, PomodoroOptionsActivity.class);
                    startActivity(pomodoro);
                }
            });
            currentAdviseNo++;
        }
        if(QuestionnaireActivity.y3){
            adviseText += "-Since you study better with background noise, we advise you to pick one of the background noise options in the settings to study with.\n\n";
            advises[currentAdviseNo].setText("Go to settings now");
            advises[currentAdviseNo].setTextSize(16);
            advises[currentAdviseNo].setTextColor(Color.BLACK);
            adviseButtons[currentAdviseNo].setVisibility(View.VISIBLE);
            adviseButtons[currentAdviseNo].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent settings = new Intent(QuestionnaireResultsActivity.this, SettingsActivity.class);
                    startActivity(settings);
                }
            });
            currentAdviseNo++;
        }
        if(QuestionnaireActivity.y4){
            adviseText += "-Since you like seeing and organizing your tasks, we advise you to use out To-Do list.\n\n";
            advises[currentAdviseNo].setText("Go to To-Do list now");
            advises[currentAdviseNo].setTextSize(16);
            advises[currentAdviseNo].setTextColor(Color.BLACK);
            adviseButtons[currentAdviseNo].setVisibility(View.VISIBLE);
            adviseButtons[currentAdviseNo].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent todo = new Intent(QuestionnaireResultsActivity.this, ToDoListActivity.class);
                    startActivity(todo);
                }
            });
            currentAdviseNo++;
        }
        if(QuestionnaireActivity.n4){
            adviseText += "-Since you don't like organizing your tasks, we advise you to study with the Leitner System which organizes your materials for you.\n\n";
            if(!leitner){
                advises[currentAdviseNo].setText("Go to leitner system now");
                advises[currentAdviseNo].setTextSize(16);
                advises[currentAdviseNo].setTextColor(Color.BLACK);
                adviseButtons[currentAdviseNo].setVisibility(View.VISIBLE);
                adviseButtons[currentAdviseNo].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent leitner = new Intent(QuestionnaireResultsActivity.this, LeitnerHomeActivity.class);
                        startActivity(leitner);
                    }
                });
                currentAdviseNo++;
                leitner = true;
            }

        }
        if(QuestionnaireActivity.y5){
            adviseText += "-Since you have a hard time memorizing terms, we advise you to study with the Leitner System.\n\n";
            if(!leitner){
                advises[currentAdviseNo].setText("Go to leitner system now");
                advises[currentAdviseNo].setTextSize(16);
                advises[currentAdviseNo].setTextColor(Color.BLACK);
                adviseButtons[currentAdviseNo].setVisibility(View.VISIBLE);
                adviseButtons[currentAdviseNo].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent leitner = new Intent(QuestionnaireResultsActivity.this, LeitnerHomeActivity.class);
                        startActivity(leitner);
                    }
                });
                currentAdviseNo++;
                leitner = true;
            }
        }

        adviseTextView.setText(adviseText);
        adviseTextView.setTextSize(16);
        adviseTextView.setTextColor(Color.WHITE);

        backResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent questionnairePage = new Intent(getBaseContext(), QuestionnaireActivity.class);
                startActivity(questionnairePage);
            }
        });
    }

}