package com.merko.bilstudy.shop;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.R;
import com.merko.bilstudy.ShopActivity;

public class ShopViewHolder extends RecyclerView.ViewHolder {
    ImageView itemImage;
    TextView itemName;
    TextView itemPrice;
    Button purchaseButton;


    public ShopViewHolder(@NonNull View itemView) {
        super(itemView);
        itemImage = itemView.findViewById(R.id.itemImage);
        itemName = itemView.findViewById(R.id.itemName);
        itemPrice = itemView.findViewById(R.id.itemPrice);
        purchaseButton = itemView.findViewById(R.id.purchaseButton);

        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purchaseButton.setText("purchased");
            }
        });

    }
}
