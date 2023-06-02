package com.merko.bilstudy.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.merko.bilstudy.R;
import com.merko.bilstudy.leitner.LeitnerContainerType;

public class LeitnerContainerAddDialog extends Dialog {
    public interface ClickListener {
        void onChooseType(LeitnerContainerType type);
    }

    private ClickListener listener;
    public LeitnerContainerAddDialog(@NonNull Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_leitner_add_container);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    public void setOnClickListener(ClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button closeButton = findViewById(R.id.lnContainerAddClose);
        Button boxButton = findViewById(R.id.lnContainerAddBox);
        Button folderButton = findViewById(R.id.lnContainerAddFolder);

        closeButton.setOnClickListener((View view) -> dismiss());

        boxButton.setOnClickListener((View view) -> {
            if(listener != null) {
                listener.onChooseType(LeitnerContainerType.BOX);
            }
            dismiss();
        });

        folderButton.setOnClickListener((View view) -> {
            if(listener != null) {
                listener.onChooseType(LeitnerContainerType.FOLDER);
            }
            dismiss();
        });
    }
}
