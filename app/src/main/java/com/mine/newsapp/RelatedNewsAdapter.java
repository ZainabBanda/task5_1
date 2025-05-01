package com.mine.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;  // ← import Glide

import java.util.List;

public class RelatedNewsAdapter extends RecyclerView.Adapter<RelatedNewsAdapter.VH> {

    private final List<NewsStory> items;

    public RelatedNewsAdapter(List<NewsStory> items) {
        this.items = items;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_related_story, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        NewsStory s = items.get(position);

        // ▶️ Load the remote image URL into your ImageView
        Glide.with(holder.iv.getContext())
            .load(s.imageUrl)
            .placeholder(R.drawable.ic_placeholder)
            .into(holder.iv);

        holder.title.setText(s.title);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView title;

        VH(@NonNull View itemView) {
            super(itemView);
            iv    = itemView.findViewById(R.id.ivRelated);
            title = itemView.findViewById(R.id.tvRelatedTitle);
        }
    }
}
