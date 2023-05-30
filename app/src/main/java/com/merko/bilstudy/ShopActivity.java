package com.merko.bilstudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.pomodoro.PomodoroSource;
import com.merko.bilstudy.shop.ShopAdapter;
import com.merko.bilstudy.shop.ShopItem;
import com.merko.bilstudy.social.Profile;
import com.merko.bilstudy.social.ProfileSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ShopActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    public static List<ShopItem> shopItems;
    CardView backButton;
    CardView categoryLamps;
    CardView categoryMugs;
    CardView categoryPens;
    CardView categoryAll;

    CardView sortButton;
    CardView sortPanel;

    Button lowToHigh;
    Button highToLow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        shopItems = new ArrayList<ShopItem>();
        recyclerView = findViewById(R.id.itemsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ShopAdapter(getApplicationContext(), shopItems));
        backButton = findViewById(R.id.backButtonPomodoro);
        categoryLamps = findViewById(R.id.categoryLamps);
        categoryMugs = findViewById(R.id.categoryMugs);
        categoryPens = findViewById(R.id.categoryPens);
        categoryAll = findViewById(R.id.categoryAll);
        sortButton = findViewById(R.id.sortButton);
        sortPanel = findViewById(R.id.sortPanel);
        lowToHigh = findViewById(R.id.lowToHigh);
        highToLow = findViewById(R.id.highToLow);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(getBaseContext(), MainActivity.class);
                startActivity(homepage);
            }
        });

        shopItems.add(new ShopItem("l", "BilStudy Red Table Lamp", "450", R.drawable.shop_lamp_red));
        shopItems.add(new ShopItem("l", "BilStudy Blue Table Lamp", "450", R.drawable.shop_lamp_blue));
        shopItems.add(new ShopItem("l", "BilStudy Green Table Lamp", "450", R.drawable.shop_lamp_green));
        shopItems.add(new ShopItem("m", "BilStudy Red Mug", "450", R.drawable.shop_mug_red));
        shopItems.add(new ShopItem("m", "BilStudy Orange Mug", "150", R.drawable.shop_mug_orange));
        shopItems.add(new ShopItem("m", "BilStudy Blue Mug", "450", R.drawable.shop_mug_blue));
        shopItems.add(new ShopItem("m", "BilStudy Green Mug", "450", R.drawable.shop_mug_green));
        shopItems.add(new ShopItem("p", "BilStudy Pen Set", "150", R.drawable.shop_pen_set2));
        shopItems.add(new ShopItem("p", "BilStudy Pen Set", "150", R.drawable.shop_pen_set));
        shopItems.add(new ShopItem("p", "BilStudy Pen Set", "150", R.drawable.shop_pen_set3));

        List<ShopItem> lamps = new ArrayList<>();
        List<ShopItem> mugs = new ArrayList<>();
        List<ShopItem> pens = new ArrayList<>();
        List<ShopItem> cheapToExpensive = new ArrayList<>(shopItems);
        List<ShopItem> expensiveToCheap = new ArrayList<>(shopItems);

        for(int i = 0; i < shopItems.size(); i++){
            if(shopItems.get(i).getType().equals("l")){
                lamps.add(shopItems.get(i));
            }
            if(shopItems.get(i).getType().equals("m")){
                mugs.add(shopItems.get(i));
            }
            if(shopItems.get(i).getType().equals("p")){
                pens.add(shopItems.get(i));
            }
        }

        Collections.sort(cheapToExpensive, Comparator.comparingInt(ShopItem::getPrice));
        Collections.sort(expensiveToCheap, Comparator.comparingInt(ShopItem::getPrice).reversed());

        categoryLamps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setAdapter(new ShopAdapter(getApplicationContext(), lamps));
            }
        });

        categoryPens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setAdapter(new ShopAdapter(getApplicationContext(), pens));
            }
        });

        categoryMugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setAdapter(new ShopAdapter(getApplicationContext(), mugs));
            }
        });

        categoryAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setAdapter(new ShopAdapter(getApplicationContext(), shopItems));
            }
        });

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortPanel.setVisibility(View.VISIBLE);
                lowToHigh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerView.setAdapter(new ShopAdapter(getApplicationContext(), cheapToExpensive));
                        sortPanel.setVisibility(View.INVISIBLE);
                    }
                });

                highToLow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerView.setAdapter(new ShopAdapter(getApplicationContext(), expensiveToCheap));
                        sortPanel.setVisibility(View.INVISIBLE);
                    }
                });


            }
        });

    }
}