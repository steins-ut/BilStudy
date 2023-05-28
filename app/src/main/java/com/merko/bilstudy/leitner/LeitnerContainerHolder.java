package com.merko.bilstudy.leitner;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.R;

public class LeitnerContainerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public interface ClickListener {
        void onItemClick(int position);
    }

    private ImageView boxImage;
    private TextView nameText;
    private TextView tagText;
    private TextView solvedText;
    private TextView boxCountText;
    private ClickListener listener;

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
        String tags = "";
        for(String t: container.tags) {
            tags += "#" + t + " ";
        }
        tagText.setText(tags);
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
