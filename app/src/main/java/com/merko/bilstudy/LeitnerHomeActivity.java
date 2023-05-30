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
import com.merko.bilstudy.leitner.LeitnerContainerType;
import com.merko.bilstudy.leitner.LeitnerSource;
import com.merko.bilstudy.ui.adapter.LeitnerContainerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LeitnerHomeActivity extends AppCompatActivity {
    private LeitnerContainerAdapter adapter;
    private List<LeitnerContainer> containers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitner_home);

        SourceLocator locator = SourceLocator.getInstance();

        RecyclerView containerRecycler = findViewById(R.id.lnHomeContainers);
        FloatingActionButton backButton = findViewById(R.id.lnQuestoinMCSBackButton);
        FloatingActionButton addButton = findViewById(R.id.lnHomeAddButton);
        FloatingActionButton deleteButton = findViewById(R.id.lnHomeEditButton);

        LoadingDialog dialog = new LoadingDialog(this);
        CompletableFuture<LeitnerContainer[]> future = locator.getSource(LeitnerSource.class).getAllContainers();
        dialog.addFutures(future);
        dialog.show();

        containers = Arrays.asList(future.join());
        List<LeitnerContainer> filteredContainers = filterContainers(containers);
        adapter = new LeitnerContainerAdapter(filteredContainers);

        adapter.setOnClickListener((int position) -> {
            Intent intent;
            LeitnerContainer container = filteredContainers.get(position);
            if(container.type == LeitnerContainerType.BOX) {
                intent = new Intent(this, LeitnerBoxActivity.class);
            }
            else {
                intent = new Intent(this, LeitnerFolderActivity.class);
            }
            intent.putExtra("CONTAINER_ID", container.uuid.toString());
            startActivity(intent);
        });

        containerRecycler.setAdapter(adapter);
        containerRecycler.setLayoutManager(new LinearLayoutManager(this));

        backButton.setOnClickListener((View view) -> finish());

        backButton.show();
        addButton.show();
        deleteButton.show();
    }

    private List<LeitnerContainer> filterContainers(List<LeitnerContainer> items) {
        ArrayList<LeitnerContainer> filteredItems = new ArrayList<>(items.size());
        for(LeitnerContainer c: items) {
            if(c.parentUuid == null) {
                filteredItems.add(c);
            }
        }
        return filteredItems;
    }
}
