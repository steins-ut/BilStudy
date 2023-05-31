package com.merko.bilstudy.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.R;
import com.merko.bilstudy.ui.holder.LeitnerQuestionMCSHolder;

import java.util.List;

public class LeitnerQuestionMCSAdapter extends RecyclerView.Adapter<LeitnerQuestionMCSHolder> {

    private List<String> choices;
    private final LeitnerQuestionMCSHolder.ClickListener listener;
    private final boolean editable;

    public LeitnerQuestionMCSAdapter(List<String> choices, LeitnerQuestionMCSHolder.ClickListener listener, boolean editable) {
        this.editable = editable;
        this.choices = choices;
        this.listener = listener;
    }

    public LeitnerQuestionMCSAdapter(List<String> choices, LeitnerQuestionMCSHolder.ClickListener listener) {
        this.editable = false;
        this.choices = choices;
        this.listener = listener;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LeitnerQuestionMCSHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int id = R.layout.entry_leitner_question_choice;
        if(editable) {
            id = R.layout.entry_leitner_add_question_choice;
        }
       View view = LayoutInflater.from(parent.getContext()).inflate(id, parent, false);
       return new LeitnerQuestionMCSHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull LeitnerQuestionMCSHolder holder, int position) {
        holder.setChoice(choices.get(position));
    }

    @Override
    public int getItemCount() {
        return choices.size();
    }
}
