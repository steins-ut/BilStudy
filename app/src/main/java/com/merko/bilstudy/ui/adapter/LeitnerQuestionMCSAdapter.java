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
    private LeitnerQuestionMCSHolder.ClickListener listener;

    public LeitnerQuestionMCSAdapter(List<String> choices, LeitnerQuestionMCSHolder.ClickListener listener) {
        this.listener = listener;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LeitnerQuestionMCSHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_leitner_question_choice, parent, false);
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
