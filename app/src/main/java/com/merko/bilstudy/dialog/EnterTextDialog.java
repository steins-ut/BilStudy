package com.merko.bilstudy.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.merko.bilstudy.R;

public class EnterTextDialog extends Dialog {
    public interface ClickListener {
        void onClick(String text);
    }

    private String text;
    private int textId;
    private ClickListener listener;

    public EnterTextDialog(@NonNull Context context) {
        super(context);
        text = context.getString(R.string.enter_text);
        textId = -1;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_enter_text);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    public EnterTextDialog(@NonNull Context context, int resId) {
        super(context);
        textId = resId;
        text = context.getString(textId);
    }

    public void setTitle(String text) {
        this.text = text;
        textId = -1;
    }

    public void setTitleResource(int resId) {
        textId = resId;
    }

    public void setOnClickListener(ClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void show() {
        super.show();
        TextView title = findViewById(R.id.dgEnterTextTitle);
        EditText field = findViewById(R.id.dgEnterTextField);
        Button closeButton = findViewById(R.id.dgEnterTextClose);
        Button okButton = findViewById(R.id.dgEnterTextOK);

        if(textId > 0) {
            title.setText(textId);
        }
        else {
            title.setText(text);
        }

        closeButton.setOnClickListener((View view) -> {
            dismiss();
        });

        okButton.setOnClickListener((View view) -> {
            if(listener != null) {
                listener.onClick(field.getText().toString());
            }
            dismiss();
        });
    }
}
