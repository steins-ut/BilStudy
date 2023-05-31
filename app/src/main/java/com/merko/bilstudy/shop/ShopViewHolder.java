package com.merko.bilstudy.shop;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.ProfileActivity;
import com.merko.bilstudy.R;
import com.merko.bilstudy.ShopActivity;
import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.social.Profile;
import com.merko.bilstudy.social.ProfileSource;

public class ShopViewHolder extends RecyclerView.ViewHolder {
    ImageView itemImage;
    TextView itemName;
    TextView itemPrice;
    Button purchaseButton;
    Context context;

    public ShopViewHolder(@NonNull View itemView){
        super(itemView);
        context = itemView.getContext();
        itemImage = itemView.findViewById(R.id.itemImage);
        itemName = itemView.findViewById(R.id.itemName);
        itemPrice = itemView.findViewById(R.id.itemPrice);
        purchaseButton = itemView.findViewById(R.id.purchaseButton);
        purchaseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                itemName = itemView.findViewById(R.id.itemName);
                itemPrice = itemView.findViewById(R.id.itemPrice);
                Profile p = null;
                boolean alreadyPurchased = false;
                try {
                    p = SourceLocator.getInstance().getSource(ProfileSource.class).getLoggedInProfile().get();
                    ProfileSource source = SourceLocator.getInstance().getSource(ProfileSource.class);
                    source.updateProfile(p).join();

                    if(p.purchasedItems != null){
                        for(int k = 0; k < p.purchasedItems.size(); k++){
                            for(int l = 0; l < ShopActivity.shopItems.size(); l++){
                                if(p.purchasedItems.get(k) == (ShopActivity.shopItems.get(l).getId())){
                                    if(ShopActivity.shopItems.get(l).getName().equals(itemName.getText().toString())){
                                        alreadyPurchased = true;
                                        Toast.makeText(context, "Every item can only be purchased once", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                    }
                    if(p.coin >= Integer.parseInt(itemPrice.getText().toString())){
                        if(!alreadyPurchased) {
                            for(int k = 0; k < ShopActivity.shopItems.size(); k++){
                                if(ShopActivity.shopItems.get(k).getName().equals(itemName.getText().toString())){
                                    p.purchasedItems.add(ShopActivity.shopItems.get(k).getId());
                                    ProfileSource sourcee = SourceLocator.getInstance().getSource(ProfileSource.class);
                                    p.coin -= Integer.parseInt(itemPrice.getText().toString());
                                    ShopActivity.updateBalance();
                                    sourcee.updateProfile(p).join();
                                    purchaseButton.setText("purchased");
                                }
                            }
                        }
                    }
                    else {
                        Toast.makeText(context, "You do not have enough coins to purchase this item", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}