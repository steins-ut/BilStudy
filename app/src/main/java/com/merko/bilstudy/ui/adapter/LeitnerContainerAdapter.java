package com.merko.bilstudy.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merko.bilstudy.R;
import com.merko.bilstudy.leitner.LeitnerContainer;
import com.merko.bilstudy.ui.holder.LeitnerContainerHolder;

import java.util.ArrayList;
import java.util.List;

public class LeitnerContainerAdapter extends RecyclerView.Adapter<LeitnerContainerHolder> {

    private List<LeitnerContainer> containers;
    private LeitnerContainerHolder.ClickListener listener;

    public LeitnerContainerAdapter(List<LeitnerContainer> containers) {
        this.containers = new ArrayList<>(containers);
    }

    @NonNull
    @Override
    public LeitnerContainerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_leitner_container, parent, false);
        return new LeitnerContainerHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull LeitnerContainerHolder holder, int position) {
        holder.setContainer(containers.get(position));
    }

    @Override
    public int getItemCount() {
        return containers.size();
    }

    public void setOnClickListener(LeitnerContainerHolder.ClickListener listener) {
        this.listener = listener;
    }
}
