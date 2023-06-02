package com.merko.bilstudy.ui.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.R;
import com.merko.bilstudy.leitner.LeitnerQuestion;

public class LeitnerQuestionHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public interface ClickListener {
        void onItemClick(int position);
        boolean onItemLongClick(int position);
    }

    private final ClickListener listener;

    public LeitnerQuestionHolder(@NonNull View itemView, ClickListener listener) {
        super(itemView);
        this.listener = listener;

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setQuestion(LeitnerQuestion question) {
        TextView orderText = itemView.findViewById(R.id.lnQuestionEntryOrder);
        TextView questionText = itemView.findViewById(R.id.lnQuestionEntryText);
        TextView questionTypeText = itemView.findViewById(R.id.lnQuestionEntryAnswerType);

        orderText.setText(getAdapterPosition() + 1 + ".");
        questionText.setText(question.question);
        questionTypeText.setText(question.type.toString());
    }

    @Override
    public void onClick(View view) {
        if(listener != null) {
            listener.onItemClick(getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if(listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
            return listener.onItemLongClick(getAdapterPosition());
        }
        return false;
    }
}
