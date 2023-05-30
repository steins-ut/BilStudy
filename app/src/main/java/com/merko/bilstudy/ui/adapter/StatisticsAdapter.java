package com.merko.bilstudy.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.R;
import com.merko.bilstudy.social.LeitnerStatistics;

import java.util.ArrayList;


public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.MyViewHolderStat> {

    Context context;
    ArrayList<LeitnerStatistics> leitnerStatistics;

    public StatisticsAdapter(Context context, ArrayList<LeitnerStatistics> leitnerStatistics){
        this.context = context;
        this.leitnerStatistics = leitnerStatistics;
    }

    @NonNull
    @Override
    public MyViewHolderStat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.entry_statistics_row,parent,false);
        return new StatisticsAdapter.MyViewHolderStat(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolderStat holder, int position) {
        holder.folder.setText(leitnerStatistics.get(position).getFolder());
        holder.allCards.setText(leitnerStatistics.get(position).getAllCards());
        holder.cardsStudied.setText(leitnerStatistics.get(position).getCardsStudied());
        holder.accuracy.setText(leitnerStatistics.get(position).getAccuracy());

    }

    @Override
    public int getItemCount() {
        return leitnerStatistics.size();
    }

    public static class MyViewHolderStat extends RecyclerView.ViewHolder {
        TextView folder,allCards,cardsStudied,accuracy;
        public MyViewHolderStat(@NonNull View itemView) {
            super(itemView);
            folder = itemView.findViewById(R.id.textView);
            allCards = itemView.findViewById(R.id.textView3);
            cardsStudied = itemView.findViewById(R.id.textView5);
            accuracy = itemView.findViewById(R.id.textView8);
        }
    }
}