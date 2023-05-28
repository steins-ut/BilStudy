package com.merko.bilstudy;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.pomodoro.PomodoroPreset;
import com.merko.bilstudy.pomodoro.PomodoroSource;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PomodoroViewHolder extends RecyclerView.ViewHolder {
    TextView optionName;
    static TextView studyDuration;
    static TextView breakDuration;

    Button studyButton;
    Button deleteButton;
    Context context;
    static boolean itemDeleted = false;
    public PomodoroViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        optionName = itemView.findViewById(R.id.optionName);
        studyDuration = itemView.findViewById(R.id.studyMinutes);
        breakDuration = itemView.findViewById(R.id.breakMinutes);
        studyButton = itemView.findViewById(R.id.studyButton);
        deleteButton = itemView.findViewById(R.id.deleteButton);
        PomodoroOptionsActivity optionsActivity = new PomodoroOptionsActivity();

        studyButton.setOnClickListener((View view) -> {
            studyDuration = itemView.findViewById(R.id.studyMinutes);
            breakDuration = itemView.findViewById(R.id.breakMinutes);
            Intent countdown = new Intent(context, PomodoroCountdownActivity.class);
            countdown.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(countdown);
        });


        deleteButton.setOnClickListener((View view) -> {
            PomodoroSource pomodoro = SourceLocator.getInstance().getSource(PomodoroSource.class);
            if(!pomodoro.equals(null)){
                CompletableFuture<PomodoroPreset[]> all = pomodoro.getAllPresets();
                List<PomodoroPreset> allList= Arrays.asList(all.join());
                for(int i = 0; i < allList.size(); i++){
                    itemDeleted = false;
                    if(allList.get(i).name.equals(optionName.getText().toString())){
                        pomodoro.deletePreset(allList.get(i).uuid);
                        itemDeleted = true;
                        Intent options = new Intent(context, PomodoroOptionsActivity.class);
                        options.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(options);
                    }
                }
            }
            PomodoroOptionsActivity.loadOptions();
        });

    }
    public static long getStudyMins(){
        return Integer.parseInt(studyDuration.getText().toString());
    }
    public static long getBreakMins(){
        return Integer.parseInt(breakDuration.getText().toString());
    }
}
