package com.mine.newsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class NewsDetailFragment extends Fragment {

    private static final String ARG_STORY = "arg_story";

    public static NewsDetailFragment newInstance(NewsStory story) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_STORY, story);
        fragment.setArguments(args);
        return fragment;
    }

    private NewsStory story;
    private ImageView iv;
    private TextView tvTitle, tvDesc;
    private RecyclerView rvRelated;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        story = (NewsStory) requireArguments().getSerializable(ARG_STORY);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              Bundle savedInstanceState) {
        iv        = view.findViewById(R.id.ivDetailImage);
        tvTitle   = view.findViewById(R.id.tvDetailTitle);
        tvDesc    = view.findViewById(R.id.tvDetailDesc);
        rvRelated = view.findViewById(R.id.rvRelated);

        // Load the main image via Glide
        Glide.with(iv.getContext())
            .load(story.imageUrl)
            .placeholder(R.drawable.ic_placeholder)
            .into(iv);

        tvTitle.setText(story.title);
        tvDesc .setText(story.content);

        // Build a list of “related” stories, reusing the same imageUrl (or supply your own)
        List<NewsStory> related = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            if (!String.valueOf(i).equals(story.id)) {
                related.add(new NewsStory(
                    String.valueOf(i),
                    "Story " + i,
                    "",      // snippet
                    "",      // content
                    story.imageUrl
                ));
            }
        }

        RelatedNewsAdapter adapter = new RelatedNewsAdapter(related);
        rvRelated.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRelated.setAdapter(adapter);
    }
}
