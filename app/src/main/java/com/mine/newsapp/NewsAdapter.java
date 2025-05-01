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

public class NewsAdapter
    extends RecyclerView.Adapter<NewsAdapter.VH> {

    public interface OnItemClickListener {
        void onItemClick(NewsStory story);
    }

    private final List<NewsStory> items;
    private final OnItemClickListener listener;

    public NewsAdapter(List<NewsStory> items,
                       OnItemClickListener listener) {
        this.items    = items;
        this.listener = listener;
    }

    /** Replace data & refresh UI */
    public void setItems(List<NewsStory> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_news, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        NewsStory s = items.get(position);

        // ▶️ Load the remote image URL into your ImageView
        Glide.with(holder.iv.getContext())
            .load(s.imageUrl)                        // imageUrl from your model
            .placeholder(R.drawable.ic_placeholder)  // fallback while loading
            .into(holder.iv);

        holder.title.setText(s.title);
        holder.snip .setText(s.snippet);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(s));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView  title, snip;

        VH(@NonNull View itemView) {
            super(itemView);
            iv    = itemView.findViewById(R.id.ivNews);
            title = itemView.findViewById(R.id.tvNewsTitle);
            snip  = itemView.findViewById(R.id.tvNewsSnippet);
        }
    }
}
