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
import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.pomodoro.PomodoroSource;
import com.merko.bilstudy.social.LocalProfileSource;
import com.merko.bilstudy.social.Profile;
import com.merko.bilstudy.social.ProfileSource;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
                itemName = itemView.findViewById(R.id.itemName);
                Profile p = null;
                try {
                    p = SourceLocator.getInstance().getSource(ProfileSource.class).getLoggedInProfile().get();
                    for(int k = 0; k < ShopActivity.shopItems.size(); k++){
                        if(ShopActivity.shopItems.get(k).getName().equals(itemName.getText().toString())){
                            p.purchasedItems.add(ShopActivity.shopItems.get(k).getUuid());
                            ProfileSource source = SourceLocator.getInstance().getSource(ProfileSource.class);
                            source.updateProfile(p).join();
                        }
                    }
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
