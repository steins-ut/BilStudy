package com.merko.bilstudy.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.R;
import com.merko.bilstudy.leitner.LeitnerContainer;
import com.merko.bilstudy.leitner.LeitnerContainerType;

public class LeitnerContainerHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public interface ClickListener {
        void onItemClick(int position);
        boolean onItemLongClick(int position);
    }

    private final ImageView containerImage;
    private final TextView nameText;
    private final TextView tagText;
    private final TextView solvedText;
    private final TextView containerText;
    private final ClickListener listener;

    public LeitnerContainerHolder(@NonNull View itemView, ClickListener listener) {
        super(itemView);

        this.listener = listener;
        containerImage = itemView.findViewById(R.id.lnContainerImage);
        nameText = itemView.findViewById(R.id.lnContainerName);
        tagText = itemView.findViewById(R.id.lnContainerTags);
        solvedText = itemView.findViewById(R.id.lnContainerSolved);
        containerText = itemView.findViewById(R.id.lnContainerBoxCount);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setContainer(LeitnerContainer container) {
        Context context = itemView.getContext();
        nameText.setText(container.name);
        StringBuilder tags = new StringBuilder();
        for(String t: container.tags) {
            tags.append("#").append(t).append(" ");
        }
        tagText.setText(tags.toString());
        solvedText.setText(context.getString(R.string.n_solved, container.objectIds.size()));
        if(container.type == LeitnerContainerType.BOX) {
            containerText.setText(context.getString(R.string.n_questions, container.objectIds.size()));
            containerImage.setImageResource(R.drawable.leitner_box);
        }
        else {
            containerText.setText(context.getString(R.string.n_boxes, container.objectIds.size()));
            containerImage.setImageResource(R.drawable.leitner_folder);
        }
    }

    @Override
    public void onClick(View view) {
        if(listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
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
