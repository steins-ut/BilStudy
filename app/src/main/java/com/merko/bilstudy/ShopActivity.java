package com.merko.bilstudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.shop.ShopAdapter;
import com.merko.bilstudy.shop.ShopItem;
import com.merko.bilstudy.social.Profile;
import com.merko.bilstudy.social.ProfileSource;

import java.util.ArrayList;
import java.util.Collection;
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
    CardView categoryPurchased;

    CardView sortButton;
    CardView sortPanel;

    Button lowToHigh;
    Button highToLow;
    static TextView balanceText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        shopItems = new ArrayList<>();
        recyclerView = findViewById(R.id.itemsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ShopAdapter(getApplicationContext(), shopItems));
        backButton = findViewById(R.id.backButtonPomodoro);
        categoryLamps = findViewById(R.id.categoryLamps);
        categoryMugs = findViewById(R.id.categoryMugs);
        categoryPens = findViewById(R.id.categoryPens);
        categoryAll = findViewById(R.id.categoryAll);
        categoryPurchased = findViewById(R.id.categoryPurchased);
        sortButton = findViewById(R.id.sortButton);
        sortPanel = findViewById(R.id.sortPanel);
        lowToHigh = findViewById(R.id.lowToHigh);
        highToLow = findViewById(R.id.highToLow);
        balanceText = findViewById(R.id.balanceText);
        updateBalance();

        backButton.setOnClickListener(v -> {
            Intent homepage = new Intent(getBaseContext(), MainActivity.class);
            startActivity(homepage);
        });

        shopItems.add(new ShopItem("l", "BilStudy Red Table Lamp", "300", R.drawable.shop_lamp_red, 1));
        shopItems.add(new ShopItem("l", "BilStudy Blue Table Lamp", "450", R.drawable.shop_lamp_blue, 2));
        shopItems.add(new ShopItem("l", "BilStudy Green Table Lamp", "250", R.drawable.shop_lamp_green, 3));
        shopItems.add(new ShopItem("m", "BilStudy Red Mug", "400", R.drawable.shop_mug_red, 4));
        shopItems.add(new ShopItem("m", "BilStudy Orange Mug", "200", R.drawable.shop_mug_orange, 5));
        shopItems.add(new ShopItem("m", "BilStudy Blue Mug", "300", R.drawable.shop_mug_blue, 6));
        shopItems.add(new ShopItem("m", "BilStudy Green Mug", "250", R.drawable.shop_mug_green, 7));
        shopItems.add(new ShopItem("p", "BilStudy Pen Set", "150", R.drawable.shop_pen_set2, 8));
        shopItems.add(new ShopItem("p", "BilStudy Colorful Pen Set", "170", R.drawable.shop_pen_set, 9));
        shopItems.add(new ShopItem("p", "BilStudy Classic Pen Set", "190", R.drawable.shop_pen_set3, 10));
        shopItems.add(new ShopItem("k", "BilStudy Normal Pen Set", "10", R.drawable.shop_pen_set3, 11));

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

        cheapToExpensive.sort(Comparator.comparingInt(ShopItem::getPrice));
        expensiveToCheap.sort(Comparator.comparingInt(ShopItem::getPrice).reversed());

        categoryLamps.setOnClickListener(v -> recyclerView.setAdapter(new ShopAdapter(getApplicationContext(), lamps)));

        categoryPens.setOnClickListener(v -> recyclerView.setAdapter(new ShopAdapter(getApplicationContext(), pens)));

        categoryMugs.setOnClickListener(v -> recyclerView.setAdapter(new ShopAdapter(getApplicationContext(), mugs)));

        categoryAll.setOnClickListener(v -> recyclerView.setAdapter(new ShopAdapter(getApplicationContext(), shopItems)));

        categoryPurchased.setOnClickListener(v ->{
            Profile p = null;
            List<ShopItem> purchased = new ArrayList<ShopItem>();
            try{
                p = SourceLocator.getInstance().getSource(ProfileSource.class).getLoggedInProfile().get();
                for(int i = 0; i < p.purchasedItems.size(); i++) {
                    for(int r = 0; r < ShopActivity.shopItems.size(); r++){
                        if(p.purchasedItems.get(i) == ShopActivity.shopItems.get(r).getId()){
                            purchased.add(ShopActivity.shopItems.get(r));
                        }
                    }
                }
                recyclerView.setAdapter(new ShopAdapter(getApplicationContext(), purchased));
            }catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });




        sortButton.setOnClickListener(v -> {
            sortPanel.setVisibility(View.VISIBLE);
            lowToHigh.setOnClickListener(v1 -> {
                recyclerView.setAdapter(new ShopAdapter(getApplicationContext(), cheapToExpensive));
                sortPanel.setVisibility(View.INVISIBLE);
            });

            highToLow.setOnClickListener(v12 -> {
                recyclerView.setAdapter(new ShopAdapter(getApplicationContext(), expensiveToCheap));
                sortPanel.setVisibility(View.INVISIBLE);
            });

            });

    }

    public static void updateBalance(){
        Profile p = null;
        try{
            p = SourceLocator.getInstance().getSource(ProfileSource.class).getLoggedInProfile().get();
            balanceText.setText("Balance: " + p.coin);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}