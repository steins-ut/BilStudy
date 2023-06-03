package com.merko.bilstudy;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.dialog.EnterTextDialog;
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
    private EditText folderName;
    private ChipGroup folderTagChips;
    private TextView folderContainerCount;
    private List<LeitnerContainer> containers;
    private List<Chip> tagChips;
    private Chip addTagChip;
    private Drawable defaultEditDrawable;
    private UUID folderId;
    private LeitnerContainer folder;
    private LeitnerContainerAdapter adapter;
    private boolean editing = false;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitner_folder);

        folderId = UUID.fromString(getIntent().getStringExtra("CONTAINER_ID"));

        containerRecycler = findViewById(R.id.lnFolderBoxRecycler);
        backButton = findViewById(R.id.lnFolderBackButton);
        editButton = findViewById(R.id.lnFolderEditButton);
        saveButton = findViewById(R.id.lnFolderSaveButton);
        addButton = findViewById(R.id.lnFolderAddButton);
        folderName = findViewById(R.id.lnFolderName);
        folderContainerCount = findViewById(R.id.lnFolderBoxes);
        folderTagChips = findViewById(R.id.lnFolderTagChipGroup);
        tagChips = new ArrayList<>();
        defaultEditDrawable = folderName.getBackground();

        folderName.setClickable(false);
        folderName.setFocusable(false);
        folderName.setFocusableInTouchMode(false);
        folderName.setBackground(null);

        SourceLocator locator = SourceLocator.getInstance();
        LeitnerSource source = locator.getSource(LeitnerSource.class);

        adapter = new LeitnerContainerAdapter(containers, this);
        reloadContainer();

        containerRecycler.setAdapter(adapter);
        containerRecycler.setLayoutManager(new LinearLayoutManager(this));

        int[] attrs = new int[] { androidx.appcompat.R.attr.colorPrimary };
        TypedArray a = obtainStyledAttributes(attrs);
        int colorId = a.getResourceId(0, R.color.bilstudy_gray);
        a.recycle();
        addTagChip = new Chip(this);
        addTagChip.setText("Add Tag");
        addTagChip.setTextColor(getColor(R.color.black));
        addTagChip.setTextAppearance(R.style.Theme_BilStudy_Leitner_TextAppearance);
        addTagChip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        addTagChip.setChipBackgroundColorResource(colorId);
        addTagChip.setCloseIconVisible(false);
        addTagChip.setChipIconResource(R.drawable.ic_add);
        addTagChip.setOnClickListener((View view) -> {
            EnterTextDialog dialog = new EnterTextDialog(this);

            dialog.setOnClickListener((String text) -> {
                createTagChip(text);
            });
            dialog.show();
        });

        addButton.setOnClickListener((View view) -> {
            LeitnerContainer newContainer = new LeitnerContainer();
            newContainer.name = getString(R.string.new_box);
            newContainer.type = LeitnerContainerType.BOX;
            newContainer.tags = new ArrayList<>();
            newContainer.objectIds = new ArrayList<>();
            newContainer.parentUuid = folderId;
            folder.objectIds = new ArrayList<>(folder.objectIds);
            CompletableFuture<UUID> newUuidFuture = source.putContainer(newContainer);
            folder.objectIds.add(newUuidFuture.join());
            source.updateContainer(folder);
            reloadContainer();
        });

        backButton.setOnClickListener((View view) -> finish());

        editButton.setOnClickListener((View view) -> {
            editing = true;
            folderName.setClickable(true);
            folderName.setFocusable(true);
            folderName.setFocusableInTouchMode(true);
            folderName.setBackground(defaultEditDrawable);
            folderName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
            for(Chip chip: tagChips) {
                chip.setCloseIconVisible(true);
            }
            folderTagChips.addView(addTagChip, 0);

            editButton.hide();
            addButton.show();
            saveButton.show();
        });

        saveButton.setOnClickListener((View view) -> {
            editing = false;
            folderName.setClickable(false);
            folderName.setFocusable(false);
            folderName.setFocusableInTouchMode(false);
            folderName.setBackground(null);
            folderName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f);
            folder.name = folderName.getText().toString();
            folderTagChips.removeViewAt(0);
            folder.tags = new ArrayList<>();
            for(Chip chip: tagChips) {
                chip.setCloseIconVisible(editing);
                folder.tags.add(chip.getText().toString().substring(1));
            }
            source.updateContainer(folder);

            editButton.show();
            addButton.hide();
            saveButton.hide();
        });

        editButton.show();
        backButton.show();
        addButton.hide();
        saveButton.hide();
    }

    private void createTagChip(String text) {
        int[] attrs = new int[] { androidx.appcompat.R.attr.colorPrimary };
        TypedArray a = obtainStyledAttributes(attrs);
        int colorId = a.getResourceId(0, R.color.bilstudy_gray);
        a.recycle();
        Chip chip = new Chip(this);
        chip.setText("#" + text);
        chip.setTextColor(getColor(R.color.black));
        chip.setTextAppearance(R.style.Theme_BilStudy_Leitner_TextAppearance);
        chip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        chip.setChipBackgroundColorResource(colorId);
        chip.setCloseIconVisible(editing);
        int finalI = tagChips.size();
        chip.setOnClickListener((View view) -> {
            if(editing) {
                tagChips.remove(finalI);
                folderTagChips.removeViewAt(finalI + 1);
            }
        });
        tagChips.add(chip);
        folderTagChips.addView(chip);
    }

    private void reloadContainer() {
        SourceLocator locator = SourceLocator.getInstance();
        LeitnerSource source = locator.getSource(LeitnerSource.class);

        LoadingDialog dialog = new LoadingDialog(this);
        CompletableFuture<LeitnerContainer> future = source.getContainer(folderId);
        dialog.addFutures(future);
        dialog.show();

        folder = future.join();
        folderName.setText(folder.name);
        tagChips.clear();
        folderTagChips.removeAllViews();
        if(editing) {
            folderTagChips.addView(addTagChip, 0);
        }
        for(int i = 0; i < folder.tags.size(); i++) {
            createTagChip(folder.tags.get(i));
        }
        folderContainerCount.setText(getString(R.string.n_boxes, folder.objectIds.size()));
        reloadSubContainers();
    }

    private void reloadSubContainers() {
        SourceLocator locator = SourceLocator.getInstance();
        LeitnerSource source = locator.getSource(LeitnerSource.class);

        LoadingDialog dialog = new LoadingDialog(this);
        CompletableFuture<LeitnerContainer[]> future = source.getContainers(folder);
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
