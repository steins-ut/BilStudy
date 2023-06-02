package com.merko.bilstudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
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
import com.merko.bilstudy.ui.holder.LeitnerContainerHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class LeitnerFolderActivity extends AppCompatActivity implements LeitnerContainerHolder.ClickListener {
    private RecyclerView containerRecycler;
    private FloatingActionButton backButton;
    private FloatingActionButton editButton;
    private FloatingActionButton saveButton;
    private FloatingActionButton addButton;
    private TextView folderName;
    private TextView folderTags;
    private TextView folderContainerCount;
    private List<LeitnerContainer> containers;
    private UUID containerId;
    private LeitnerContainer container;
    private LeitnerContainerAdapter adapter;
    private boolean editing = false;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitner_folder);

        containerId = UUID.fromString(getIntent().getStringExtra("CONTAINER_ID"));

        containerRecycler = findViewById(R.id.lnFolderBoxRecycler);
        backButton = findViewById(R.id.lnFolderBackButton);
        editButton = findViewById(R.id.lnFolderEditButton);
        saveButton = findViewById(R.id.lnFolderSaveButton);
        addButton = findViewById(R.id.lnFolderAddButton);
        folderName = findViewById(R.id.lnFolderName);
        folderTags = findViewById(R.id.lnFolderTags);
        folderContainerCount = findViewById(R.id.lnFolderBoxes);

        SourceLocator locator = SourceLocator.getInstance();
        LeitnerSource source = locator.getSource(LeitnerSource.class);

        adapter = new LeitnerContainerAdapter(containers, this);
        reloadContainer();

        containerRecycler.setAdapter(adapter);
        containerRecycler.setLayoutManager(new LinearLayoutManager(this));

        addButton.setOnClickListener((View view) -> {
            LeitnerContainer newContainer = new LeitnerContainer();
            newContainer.name = getString(R.string.new_box);
            newContainer.type = LeitnerContainerType.BOX;
            newContainer.tags = new ArrayList<>();
            newContainer.objectIds = new ArrayList<>();
            newContainer.parentUuid = containerId;
            container.objectIds = new ArrayList<>(container.objectIds);
            CompletableFuture<UUID> newUuidFuture = source.putContainer(newContainer);
            container.objectIds.add(newUuidFuture.join());
            source.updateContainer(container);
            reloadContainer();
        });

        backButton.setOnClickListener((View view) -> finish());

        editButton.setOnClickListener((View view) -> {
            editing = true;

            editButton.hide();
            addButton.show();
            saveButton.show();
        });

        saveButton.setOnClickListener((View view) -> {
            editing = false;

            editButton.show();
            addButton.hide();
            saveButton.hide();
        });

        editButton.show();
        backButton.show();
        addButton.hide();
        saveButton.hide();
    }

    private void reloadContainer() {
        SourceLocator locator = SourceLocator.getInstance();
        LeitnerSource source = locator.getSource(LeitnerSource.class);

        LoadingDialog dialog = new LoadingDialog(this);
        CompletableFuture<LeitnerContainer> future = source.getContainer(containerId);
        dialog.addFutures(future);
        dialog.show();

        container = future.join();
        folderName.setText(container.name);
        StringBuilder tags = new StringBuilder();
        for(String t: container.tags) {
            tags.append("#").append(t).append(" ");
        }
        folderTags.setText(tags.toString());
        folderContainerCount.setText(getString(R.string.n_boxes, container.objectIds.size()));
        reloadSubContainers();
    }

    private void reloadSubContainers() {
        SourceLocator locator = SourceLocator.getInstance();
        LeitnerSource source = locator.getSource(LeitnerSource.class);

        LoadingDialog dialog = new LoadingDialog(this);
        CompletableFuture<LeitnerContainer[]> future = source.getContainers(container);
        dialog.addFutures(future);
        dialog.show();

        containers = new ArrayList<>(Arrays.asList(future.join()));
        adapter.setContainers(containers);
    }

    @Override
    public void onItemClick(int position) {
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
    }

    @Override
    public boolean onItemLongClick(int position) {
        if(editing) {
            SourceLocator locator = SourceLocator.getInstance();
            LeitnerSource source = locator.getSource(LeitnerSource.class);
            RecyclerView.ViewHolder holder = containerRecycler.findViewHolderForAdapterPosition(position);
            if(holder == null) {
                return false;
            }

            PopupMenu menu = new PopupMenu(this, holder.itemView);
            menu.setOnMenuItemClickListener((MenuItem item) -> {
                LeitnerContainer container = containers.get(position);
                containers.remove(position);
                source.deleteContainer(container.uuid);
                adapter.notifyDataSetChanged();
                return true;
            });

            menu.inflate(R.menu.popup_leitner_container);
            menu.show();
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadContainer();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        reloadContainer();
    }
}
