package com.merko.bilstudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.dialog.LoadingDialog;
import com.merko.bilstudy.leitner.LeitnerContainer;
import com.merko.bilstudy.leitner.LeitnerContainerAdapter;
import com.merko.bilstudy.leitner.LeitnerContainerType;
import com.merko.bilstudy.leitner.LeitnerSource;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LeitnerHomeActivity extends AppCompatActivity {
    private LeitnerContainerAdapter adapter;
    private List<LeitnerContainer> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitner_home);

        SourceLocator locator = SourceLocator.getInstance();

        RecyclerView containerRecycler = findViewById(R.id.lnHomeContainers);
        FloatingActionButton backButton = findViewById(R.id.lnHomeBackButton);
        FloatingActionButton addButton = findViewById(R.id.lnHomeAddButton);
        FloatingActionButton deleteButton = findViewById(R.id.lnHomeDeleteButton);

        LoadingDialog dialog = new LoadingDialog(this);
        CompletableFuture<LeitnerContainer[]> future = locator.getSource(LeitnerSource.class).getAllContainers();
        dialog.addFutures(future);
        dialog.show();

        try {
            items = Arrays.asList(future.get());
            adapter = new LeitnerContainerAdapter(items);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        adapter.setOnClickListener((int position) -> {
            Intent intent;
            LeitnerContainer container = items.get(position);
            if(container.type == LeitnerContainerType.BOX) {
                intent = new Intent(this, LeitnerBoxActivity.class);
            }
            else {
                intent = new Intent(this, LeitnerFolderActivity.class);
            }
            intent.putExtra("CONTAINER_ID", items.get(position).uuid);
            startActivity(intent);
        });

        containerRecycler.setAdapter(adapter);
        containerRecycler.setLayoutManager(new LinearLayoutManager(this));

        backButton.setOnClickListener((View view) -> finish());

        backButton.show();
        addButton.show();
        deleteButton.show();
    }


}
