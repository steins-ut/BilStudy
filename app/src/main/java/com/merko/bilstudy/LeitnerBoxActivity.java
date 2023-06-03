package com.merko.bilstudy;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.dialog.EnterTextDialog;
import com.merko.bilstudy.dialog.LeitnerQuestionAddDialog;
import com.merko.bilstudy.dialog.LoadingDialog;
import com.merko.bilstudy.leitner.LeitnerContainer;
import com.merko.bilstudy.leitner.LeitnerQuestion;
import com.merko.bilstudy.leitner.LeitnerQuestionType;
import com.merko.bilstudy.leitner.LeitnerSource;
import com.merko.bilstudy.social.LeitnerBoxStatistics;
import com.merko.bilstudy.social.LeitnerQuestionStatistics;
import com.merko.bilstudy.social.LeitnerStatistics;
import com.merko.bilstudy.social.Profile;
import com.merko.bilstudy.social.ProfileSource;
import com.merko.bilstudy.ui.adapter.LeitnerQuestionAdapter;
import com.merko.bilstudy.ui.holder.LeitnerQuestionHolder;
import com.merko.bilstudy.utils.Globals;
import com.merko.bilstudy.utils.LeitnerUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class LeitnerBoxActivity extends AppCompatActivity implements LeitnerQuestionHolder.ClickListener {

    private RecyclerView questionRecycler;
    private FloatingActionButton backButton;
    private FloatingActionButton playButton;
    private FloatingActionButton addButton;
    private FloatingActionButton saveButton;
    private FloatingActionButton editButton;
    private EditText boxName;
    private ChipGroup boxTagChipGroup;
    private TextView boxQuestionCount;
    private Button allButton;
    private Button weeklyButton;
    private Button thridailyButton;
    private Button dailyButton;
    private Drawable defaultEditDrawable;
    private LeitnerQuestionAdapter adapter;
    private List<LeitnerQuestion> questions;
    private List<LeitnerQuestion> filteredQuestions;
    private List<Chip> tagChips;
    private Chip addTagChip;
    private LeitnerContainer box;
    private boolean editing = false;
    private boolean allSolved = false;
    private LeitnerBoxStatistics boxStatistics;
    private LeitnerQuestionStatistics.Frequency filterFrequency;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitner_box);

        SourceLocator locator = SourceLocator.getInstance();

        questionRecycler = findViewById(R.id.lnBoxQuestionsRecycler);
        backButton = findViewById(R.id.lnBoxBackButton);
        playButton = findViewById(R.id.lnBoxPlayButton);
        addButton = findViewById(R.id.lnBoxAddButton);
        saveButton = findViewById(R.id.lnBoxSaveButton);
        editButton = findViewById(R.id.lnBoxEditButton);
        boxName = findViewById(R.id.lnBoxName);
        boxTagChipGroup = findViewById(R.id.lnBoxTagChipGroup);
        boxQuestionCount = findViewById(R.id.lnBoxQuestions);
        allButton = findViewById(R.id.lnBoxAll);
        weeklyButton =findViewById(R.id.lnBoxWeekly);
        thridailyButton = findViewById(R.id.lnBoxThridaily);
        dailyButton = findViewById(R.id.lnBoxDaily);

        tagChips = new ArrayList<>();
        defaultEditDrawable = boxName.getBackground();
        adapter = new LeitnerQuestionAdapter(null, this);
        questionRecycler.setAdapter(adapter);
        questionRecycler.setLayoutManager(new LinearLayoutManager(this));

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
        reloadBox();

        backButton.setOnClickListener((View view) -> finish());
        playButton.setOnClickListener((View view) -> {
            if(questions.size() < 1) {
                return;
            }
            if(allSolved) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("All questions are solved.\nWait until the next time you can solve one.");
                builder.setPositiveButton("OK", null);

                AlertDialog dialog = builder.create();
                dialog.show();
                return;
            }
            Intent intent = LeitnerUtils.getQuestionIntent(this, questions.get(0).type);
            intent.putExtra("BOX_ID", box.uuid.toString());
            startActivity(intent);
        });

        editButton.setOnClickListener((View view) -> {
            editing = true;
            boxName.setClickable(true);
            boxName.setFocusable(true);
            boxName.setFocusableInTouchMode(true);
            boxName.setBackground(defaultEditDrawable);
            boxName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
            for(Chip chip: tagChips) {
                chip.setCloseIconVisible(true);
            }
            boxTagChipGroup.addView(addTagChip, 0);

            editButton.hide();
            playButton.hide();
            saveButton.show();
            addButton.show();
        });

        saveButton.setOnClickListener((View view) -> {
            editing = false;
            boxName.setClickable(false);
            boxName.setFocusable(false);
            boxName.setFocusableInTouchMode(false);
            boxName.setBackground(null);
            boxName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f);
            box.name = boxName.getText().toString();
            boxTagChipGroup.removeViewAt(0);
            box.tags = new ArrayList<>();
            for(Chip chip: tagChips) {
                chip.setCloseIconVisible(false);
                box.tags.add(chip.getText().toString().substring(1));
            }
            locator.getSource(LeitnerSource.class).updateContainer(box);

            editButton.show();
            playButton.show();
            saveButton.hide();
            addButton.hide();
        });

        addButton.setOnClickListener((View view) -> {
            LeitnerQuestionAddDialog addDialog = new LeitnerQuestionAddDialog(this);
            addDialog.setOnClickListener((LeitnerQuestionType type) -> {
                Intent intent = LeitnerUtils.getAddQuestionIntent(this, type);
                intent.putExtra("QUESTION_NUMBER", questions.size());
                intent.putExtra("BOX_ID", box.uuid.toString());
                startActivity(intent);
            });
            addDialog.show();
        });

        allButton.setOnClickListener((View view) -> setFilterFrequency(null));

        weeklyButton.setOnClickListener((View view) -> setFilterFrequency(LeitnerQuestionStatistics.Frequency.WEEKLY));

        thridailyButton.setOnClickListener((View view) -> setFilterFrequency(LeitnerQuestionStatistics.Frequency.THRIDAILY));

        dailyButton.setOnClickListener((View view) -> setFilterFrequency(LeitnerQuestionStatistics.Frequency.DAILY));

        saveButton.hide();
        addButton.hide();
        backButton.show();
        playButton.show();
        editButton.show();

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
                boxTagChipGroup.removeViewAt(finalI + 1);
            }
        });
        tagChips.add(chip);
        boxTagChipGroup.addView(chip);
    }

    private void reloadBox() {
        UUID boxId = UUID.fromString(getIntent().getStringExtra("CONTAINER_ID"));
        SourceLocator locator = SourceLocator.getInstance();
        LeitnerSource source = locator.getSource(LeitnerSource.class);

        LoadingDialog containerDialog = new LoadingDialog(this);
        CompletableFuture<LeitnerContainer> containerFuture = source.getContainer(boxId);
        containerDialog.addFutures(containerFuture);
        containerDialog.show();

        box = containerFuture.join();

        tagChips.clear();
        boxTagChipGroup.removeAllViews();
        if(editing) {
            boxTagChipGroup.addView(addTagChip, 0);
            boxName.setClickable(true);
            boxName.setFocusable(true);
            boxName.setFocusableInTouchMode(true);
            boxName.setBackground(defaultEditDrawable);
            boxName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        }
        else {
            boxName.setText(box.name);
            boxName.setClickable(false);
            boxName.setFocusable(false);
            boxName.setFocusableInTouchMode(false);
            boxName.setBackground(null);
        }
        for(int i = 0; i < box.tags.size(); i++) {
            createTagChip(box.tags.get(i));
        }
        boxQuestionCount.setText(getString(R.string.n_questions, box.objectIds.size()));
        box.objectIds = new ArrayList<>(box.objectIds);

        LoadingDialog questionDialog = new LoadingDialog(this);
        CompletableFuture<LeitnerQuestion[]> questionFuture = source.getQuestions(box);
        questionDialog.addFutures(questionFuture);
        questionDialog.show();
        questions = new ArrayList<>(Arrays.asList(questionFuture.join()));
        checkUserForStatistics();
        filterQuestions();
    }

    private void setFilterFrequency(LeitnerQuestionStatistics.Frequency frequency) {
        filterFrequency = frequency;
        filterQuestions();
    }

    private void filterQuestions() {
        if(filterFrequency != null) {
            filteredQuestions = new ArrayList<>();
            for(LeitnerQuestion question: questions) {
                if(boxStatistics.questionStatistics.get(question.uuid).frequency == filterFrequency) {
                    filteredQuestions.add(question);
                }
            }
        }
        else {
            filteredQuestions = new ArrayList<>(questions);
        }
        adapter.setQuestions(filteredQuestions);
    }

    private void checkUserForStatistics() {
        ProfileSource source = SourceLocator.getInstance().getSource(ProfileSource.class);
        Profile profile = source.getLoggedInProfile().join();
        LeitnerStatistics leitnerStatistics = profile.statistics.leitnerStatistics;
        if(!leitnerStatistics.containerStatistics.containsKey(box.uuid)) {
            leitnerStatistics.containerStatistics.put(box.uuid, new LeitnerBoxStatistics());
        }
        boxStatistics = leitnerStatistics.containerStatistics.get(box.uuid);
        int solvedCount = 0;
        for(LeitnerQuestion question: questions) {
            if(!boxStatistics.questionStatistics.containsKey(question.uuid)) {
                boxStatistics.questionStatistics.put(question.uuid, new LeitnerQuestionStatistics());
            }
            LeitnerQuestionStatistics.Frequency frequency = boxStatistics.questionStatistics.get(question.uuid).frequency;
            if(System.currentTimeMillis() - boxStatistics.questionStatistics.get(question.uuid).solveDate < frequency.getDayInterval() * Globals.DAY_TO_SECONDS) {
                solvedCount++;
            }
            allSolved = solvedCount == questions.size();
        }
        source.updateProfile(profile);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadBox();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        reloadBox();
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public boolean onItemLongClick(int position) {
        if(editing) {
            SourceLocator locator = SourceLocator.getInstance();
            LeitnerSource source = locator.getSource(LeitnerSource.class);
            RecyclerView.ViewHolder holder =  questionRecycler.findViewHolderForAdapterPosition(position);
            if(holder == null) {
                return false;
            }

            PopupMenu menu = new PopupMenu(this, holder.itemView);
            menu.setOnMenuItemClickListener((MenuItem item) -> {
                LeitnerQuestion question = filteredQuestions.get(position);
                questions.remove(question);
                box.objectIds.remove(question.uuid);
                source.deleteQuestion(question.uuid).join();
                adapter.setQuestions(questions);
                return true;
            });

            menu.inflate(R.menu.popup_leitner_question);
            menu.show();
            return true;
        }
        return false;
    }
}
