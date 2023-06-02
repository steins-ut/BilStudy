package com.merko.bilstudy.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.merko.bilstudy.R;
import com.merko.bilstudy.leitner.LeitnerQuestionType;

public class LeitnerQuestionAddDialog extends Dialog {
    public interface ClickListener {
        void onChooseType(LeitnerQuestionType type);
    }

    private ClickListener listener;
    public LeitnerQuestionAddDialog(@NonNull Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_leitner_add_question);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    public void setOnClickListener(ClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button closeButton = findViewById(R.id.lnQuestionAddClose);
        Button textAnswer = findViewById(R.id.lnQuestionAddText);
        Button multipleChoiceSingle = findViewById(R.id.lnQuestionAddMCS);

        closeButton.setOnClickListener((View view) -> dismiss());

        textAnswer.setOnClickListener((View view) -> {
            if(listener != null) {
                listener.onChooseType(LeitnerQuestionType.TEXT_ANSWER);
            }
            dismiss();
        });

        multipleChoiceSingle.setOnClickListener((View view) -> {
            if(listener != null) {
                listener.onChooseType(LeitnerQuestionType.MULTIPLE_CHOICE_SINGLE);
            }
            dismiss();
        });
    }
}
