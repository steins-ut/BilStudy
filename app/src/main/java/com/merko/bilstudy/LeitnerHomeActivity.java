package com.merko.bilstudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.dialog.LeitnerContainerAddDialog;
import com.merko.bilstudy.dialog.LoadingDialog;
import com.merko.bilstudy.leitner.LeitnerContainer;
import com.merko.bilstudy.leitner.LeitnerContainerType;
import com.merko.bilstudy.leitner.LeitnerSource;
import com.merko.bilstudy.ui.adapter.LeitnerContainerAdapter;
import com.merko.bilstudy.ui.holder.LeitnerContainerHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LeitnerHomeActivity extends AppCompatActivity implements LeitnerContainerHolder.ClickListener {
    private RecyclerView containerRecycler;
    private LeitnerContainerAdapter adapter;
    private List<LeitnerContainer> filteredContainers;
    private boolean editing = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitner_home);

        SourceLocator locator = SourceLocator.getInstance();
        LeitnerSource source = locator.getSource(LeitnerSource.class);

        containerRecycler = findViewById(R.id.lnHomeContainers);
        FloatingActionButton backButton = findViewById(R.id.lnHomeBackButton);
        FloatingActionButton addButton = findViewById(R.id.lnHomeAddButton);
        FloatingActionButton editButton = findViewById(R.id.lnHomeEditButton);
        FloatingActionButton saveButton = findViewById(R.id.lnHomeSaveButton);

        adapter = new LeitnerContainerAdapter(null, this);

        containerRecycler.setAdapter(adapter);
        containerRecycler.setLayoutManager(new LinearLayoutManager(this));

        backButton.setOnClickListener((View view) -> finish());
        addButton.setOnClickListener((View view) -> {
            LeitnerContainerAddDialog addDialog = new LeitnerContainerAddDialog(this);
            addDialog.setOnClickListener((LeitnerContainerType type) -> {
                LeitnerContainer container = new LeitnerContainer();
                container.name = type == LeitnerContainerType.BOX ? getString(R.string.new_box)
                        : getString(R.string.new_folder);
                container.type = type;
                container.tags = new ArrayList<>();
                container.objectIds = new ArrayList<>();
                source.putContainer(container).join();
                reloadContainers();
            });
            addDialog.show();
        });

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

    private List<LeitnerContainer> filterContainers(List<LeitnerContainer> items) {
        ArrayList<LeitnerContainer> filteredItems = new ArrayList<>(items.size());
        for(LeitnerContainer c: items) {
            if(c.parentUuid == null) {
                filteredItems.add(c);
            }
        }
        return filteredItems;
    }

    private void reloadContainers() {
        SourceLocator locator = SourceLocator.getInstance();
        LeitnerSource source = locator.getSource(LeitnerSource.class);

        LoadingDialog dialog = new LoadingDialog(this);
        CompletableFuture<LeitnerContainer[]> future = source.getAllContainers();
        dialog.addFutures(future);
        dialog.show();

        List<LeitnerContainer> containers = Arrays.asList(future.join());
        filteredContainers = filterContainers(containers);
        adapter.setContainers(filteredContainers);
    }

    protected void onResume() {
        super.onResume();
        reloadContainers();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        reloadContainers();
    }

    @Override
    public void onItemClick(int position) {
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
                LeitnerContainer container = filteredContainers.get(position);
                filteredContainers.remove(position);
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
}
