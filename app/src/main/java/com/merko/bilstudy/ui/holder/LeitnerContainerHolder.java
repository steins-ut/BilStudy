package com.merko.bilstudy.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.R;
import com.merko.bilstudy.leitner.LeitnerContainer;
import com.merko.bilstudy.leitner.LeitnerContainerType;

public class LeitnerContainerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public interface ClickListener {
        void onItemClick(int position);
    }

    private final ImageView boxImage;
    private final TextView nameText;
    private final TextView tagText;
    private final TextView solvedText;
    private final TextView boxCountText;
    private final ClickListener listener;

    public LeitnerContainerHolder(@NonNull View itemView, ClickListener listener) {
        super(itemView);

        this.listener = listener;
        boxImage = itemView.findViewById(R.id.lnContainerImage);
        nameText = itemView.findViewById(R.id.lnContainerName);
        tagText = itemView.findViewById(R.id.lnContainerTags);
        solvedText = itemView.findViewById(R.id.lnContainerSolved);
        boxCountText = itemView.findViewById(R.id.lnContainerBoxCount);

        itemView.setOnClickListener(this);
    }

    public void setContainer(LeitnerContainer container) {
        nameText.setText(container.name);
        StringBuilder tags = new StringBuilder();
        for(String t: container.tags) {
            tags.append("#").append(t).append(" ");
        }
        tagText.setText(tags.toString());
        String solved = "Solved: %d/%d";
        String boxCount = "%d Boxes";
        if(container.type == LeitnerContainerType.BOX) {
            solvedText.setText(String.format(solved, container.objectIds.size(), container.objectIds.size()));
            boxCountText.setVisibility(View.INVISIBLE);
        }
        else {
            solvedText.setText(String.format(solved, container.objectIds.size(), container.objectIds.size()));
            boxCountText.setText(String.format(boxCount, container.objectIds.size()));
        }
    }

    @Override
    public void onClick(View view) {
        if(listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
            listener.onItemClick(getAdapterPosition());
        }
    }

}
