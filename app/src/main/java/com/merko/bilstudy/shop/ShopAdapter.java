package com.merko.bilstudy.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.R;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopViewHolder> {
    Context context;
    List<ShopItem> shopItems;

    public ShopAdapter(Context context, List<ShopItem> shopItems) {
        this.context = context;
        this.shopItems = shopItems;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShopViewHolder(LayoutInflater.from(context).inflate(R.layout.shop_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        holder.itemName.setText(shopItems.get(position).getName());
        holder.itemPrice.setText(shopItems.get(position).getPrice());
        holder.itemImage.setImageResource(shopItems.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return shopItems.size();
    }
}
