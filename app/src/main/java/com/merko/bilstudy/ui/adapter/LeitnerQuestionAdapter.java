package com.merko.bilstudy.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.R;
import com.merko.bilstudy.leitner.LeitnerQuestion;
import com.merko.bilstudy.ui.holder.LeitnerQuestionHolder;

import java.util.List;

public class LeitnerQuestionAdapter extends RecyclerView.Adapter<LeitnerQuestionHolder> {

    private List<LeitnerQuestion> questions;
    private final LeitnerQuestionHolder.ClickListener listener;

    public LeitnerQuestionAdapter(List<LeitnerQuestion> questions, LeitnerQuestionHolder.ClickListener listener) {
        this.questions = questions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LeitnerQuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_leitner_question, parent, false);
        return new LeitnerQuestionHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull LeitnerQuestionHolder holder, int position) {
        holder.setQuestion(questions.get(position));
    }

    public void setQuestions(List<LeitnerQuestion> questions) {
        this.questions = questions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
