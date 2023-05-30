package com.merko.bilstudy.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.merko.bilstudy.MainActivity;
import com.merko.bilstudy.R;
import com.merko.bilstudy.ShopActivity;
import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.social.Profile;
import com.merko.bilstudy.social.ProfileSource;

import java.util.concurrent.ExecutionException;


public class PopUpDialog extends Dialog {

    CardView closeButton;
    Context context;
    TextView coinText;
    TextView currentBalance;
    String type;
    long coins;



    public PopUpDialog(@NonNull Context context, int themeResId, String type, long coins) {
        super(context, themeResId);
        this.context = context;
        this.type = type;
        this.coins = coins;

        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_popup_coin);
        closeButton = findViewById(R.id.closePopUp);
        coinText = findViewById(R.id.coinUpdateText);
        currentBalance = findViewById(R.id.currentBalance);

        coinText.setText(coins + " coins have been added to your balance \nfor studying with the " + type);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }
}
