package com.merko.bilstudy.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.R;
import com.merko.bilstudy.social.OldStudies;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder1> {
    Context context;
    ArrayList<OldStudies>oldStudies;
    public HistoryAdapter(Context context, ArrayList<OldStudies> oldStudies){
        this.context = context;
        this.oldStudies = oldStudies;
    }

    @NonNull
    @Override
    public HistoryAdapter.MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.entry_history_row,parent,false);
        return new HistoryAdapter.MyViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyViewHolder1 holder, int position) {
    holder.date.setText(oldStudies.get(position).getDate());
    holder.study.setText(oldStudies.get(position).getStudy());
    holder.imageView.setImageResource(oldStudies.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return oldStudies.size();
    }
    public static class MyViewHolder1 extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView date,study;
        public MyViewHolder1(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewCheck);
            date = itemView.findViewById(R.id.dateTEXT);
            study = itemView.findViewById(R.id.textViewStudy);
        }
    }
}
