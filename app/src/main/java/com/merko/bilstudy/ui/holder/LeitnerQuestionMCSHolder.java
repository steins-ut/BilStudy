package com.merko.bilstudy.ui.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.R;

public class LeitnerQuestionMCSHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public interface ClickListener {
        void onItemClick(int position);
    }

    private final ClickListener listener;

    public LeitnerQuestionMCSHolder(@NonNull View itemView, ClickListener listener) {
        super(itemView);

        this.listener = listener;

        itemView.setOnClickListener(this);
    }

    public void setChoice(String choice) {
        TextView choiceSymbol = itemView.findViewById(R.id.lnQuestionChoiceEntryOrder);
        TextView choiceText = itemView.findViewById(R.id.lnQuestionChoiceEntryChoice);

        choiceSymbol.setText("" + (char)('A' + getAdapterPosition()));
        choiceText.setText(choice);
    }

    @Override
    public void onClick(View view) {
        if(listener != null) {
            listener.onItemClick(getAdapterPosition());
        }
    }

}
