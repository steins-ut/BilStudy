package com.merko.bilstudy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.merko.bilstudy.shop.ShopAdapter;
import com.merko.bilstudy.shop.ShopItem;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ShopItem> shopItems;
    CardView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        shopItems = new ArrayList<ShopItem>();
        recyclerView = findViewById(R.id.itemsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ShopAdapter(getApplicationContext(), shopItems));
        backButton = findViewById(R.id.backButtonPomodoro);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(getBaseContext(), MainActivity.class);
                startActivity(homepage);
            }
        });

        shopItems.add(new ShopItem("m", "BilStudy Coffee Mug", "250", R.drawable.shop_mug_black));
        shopItems.add(new ShopItem("l", "BilStudy Table Lamp", "450", R.drawable.shop_mug_black));
        shopItems.add(new ShopItem("p", "BilStudy Pen Set", "150", R.drawable.shop_mug_black));
        shopItems.add(new ShopItem("l", "BilStudy Table Lamp", "450", R.drawable.shop_mug_black));
        shopItems.add(new ShopItem("p", "BilStudy Pen Set", "150", R.drawable.shop_mug_black));
    }
}