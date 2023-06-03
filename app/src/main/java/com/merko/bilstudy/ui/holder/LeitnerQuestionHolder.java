package com.merko.bilstudy.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.R;
import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.leitner.LeitnerQuestion;
import com.merko.bilstudy.social.LeitnerQuestionStatistics;
import com.merko.bilstudy.social.Profile;
import com.merko.bilstudy.social.ProfileSource;

public class LeitnerQuestionHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private TextView orderText;
    private TextView questionText;
    private TextView questionTypeText;
    private ImageView questionStatus;

    public interface ClickListener {
        void onItemClick(int position);
        boolean onItemLongClick(int position);
    }

    private final ClickListener listener;

    public LeitnerQuestionHolder(@NonNull View itemView, ClickListener listener) {
        super(itemView);
        orderText = itemView.findViewById(R.id.lnQuestionEntryOrder);
        questionText = itemView.findViewById(R.id.lnQuestionEntryText);
        questionTypeText = itemView.findViewById(R.id.lnQuestionEntryAnswerType);
        questionStatus = itemView.findViewById(R.id.lnQuestionEntryStatus);
        this.listener = listener;

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setQuestion(LeitnerQuestion question) {
        ProfileSource source = SourceLocator.getInstance().getSource(ProfileSource.class);
        Profile profile = source.getLoggedInProfile().join();
        LeitnerQuestionStatistics statistics = profile.statistics
                .leitnerStatistics.containerStatistics.get(question.containerId)
                .questionStatistics.get(question.uuid);

        orderText.setText(getAdapterPosition() + 1 + ".");
        questionText.setText(question.question);
        questionTypeText.setText(question.type.toString());
        int imageId;
        switch(statistics.solved) {
            case CORRECT:
                imageId = R.drawable.ic_question_correct;
                break;
            case INCORRECT:
                imageId = R.drawable.ic_question_incorrect;
                break;
            default:
                imageId = R.drawable.ic_question_unsolved;
                break;
        }
        questionStatus.setImageResource(imageId);
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
