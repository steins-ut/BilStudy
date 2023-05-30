package com.merko.bilstudy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.pomodoro.PomodoroPreset;

import java.util.List;

public class PomodoroAdapter extends RecyclerView.Adapter<PomodoroViewHolder> {

    Context context;
    List<PomodoroPreset> pomodoroOptions;

    public PomodoroAdapter(Context context, List<PomodoroPreset> pomodoroOptions) {
        this.context = context;
        this.pomodoroOptions = pomodoroOptions;
    }

    @NonNull
    @Override
    public PomodoroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PomodoroViewHolder(
                LayoutInflater.from(context).inflate(R.layout.entry_pomodoro_preset, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PomodoroViewHolder holder, int position) {
        holder.optionName.setText(pomodoroOptions.get(position).name);
        holder.studyDuration.setText("" + pomodoroOptions.get(position).studyMinutes);
        holder.breakDuration.setText("" + pomodoroOptions.get(position).breakMinutes);
    }

    @Override
    public int getItemCount() {
        return pomodoroOptions.size();
    }

    public void setOptions(List<PomodoroPreset> set) {
        this.pomodoroOptions = set;
        notifyDataSetChanged();
    }
}
