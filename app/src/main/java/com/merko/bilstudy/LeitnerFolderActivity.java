package com.merko.bilstudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class LeitnerFolderActivity extends AppCompatActivity {
    private List<LeitnerContainer> containers;
    private UUID containerId;
    private LeitnerContainerAdapter adapter;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitner_folder);

        containerId = UUID.fromString(getIntent().getStringExtra("CONTAINER_ID"));

        SourceLocator locator = SourceLocator.getInstance();
        LeitnerSource source = locator.getSource(LeitnerSource.class);

        RecyclerView questionRecycler = findViewById(R.id.lnFolderBoxRecycler);
        FloatingActionButton backButton = findViewById(R.id.lnFolderBackButton);
        FloatingActionButton playButton = findViewById(R.id.lnFolderPlayButton);
        FloatingActionButton editButton = findViewById(R.id.lnFolderEditButton);
        TextView folderName = findViewById(R.id.lnFolderName);
        TextView folderTags = findViewById(R.id.lnFolderTags);
        TextView folderContainerCount = findViewById(R.id.lnFolderBoxes);

        LoadingDialog dialog = new LoadingDialog(this);
        CompletableFuture<LeitnerContainer> future = source.getContainer(containerId);
        dialog.addFutures(future);
        dialog.show();

        LeitnerContainer container = future.join();
        folderName.setText(container.name);
        StringBuilder tags = new StringBuilder();
        for(String t: container.tags) {
            tags.append("#").append(t).append(" ");
        }
        folderTags.setText(tags.toString());
        folderContainerCount.setText(getString(R.string.n_boxes, container.objectIds.size()));

        LoadingDialog dialog2 = new LoadingDialog(this);
        CompletableFuture<LeitnerContainer[]> future2 = source.getContainers(container);
        dialog2.addFutures(future2);
        dialog2.show();

        containers = Arrays.asList(future2.join());
        adapter = new LeitnerContainerAdapter(containers);

        adapter.setOnClickListener((int position) -> {
            Intent intent;
            LeitnerContainer selected = containers.get(position);
            if(selected.type == LeitnerContainerType.BOX) {
                intent = new Intent(this, LeitnerBoxActivity.class);
            }
            else {
                intent = new Intent(this, LeitnerFolderActivity.class);
            }
            intent.putExtra("CONTAINER_ID", selected.uuid.toString());
            startActivity(intent);
        });

        questionRecycler.setAdapter(adapter);
        questionRecycler.setLayoutManager(new LinearLayoutManager(this));

        backButton.setOnClickListener((View view) -> finish());

        backButton.show();
        playButton.show();
        editButton.show();
    }
}
