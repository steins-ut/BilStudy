package com.merko.bilstudy;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PomodoroViewHolder extends RecyclerView.ViewHolder {
    TextView optionName;
    TextView studyDuration;
    TextView breakDuration;
    public PomodoroViewHolder(@NonNull View itemView) {
        super(itemView);
        optionName = itemView.findViewById(R.id.optionName);
        studyDuration = itemView.findViewById(R.id.studyMinutes);
        breakDuration = itemView.findViewById(R.id.breakMinutes);
    }
}
