package com.mine.newsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsListFragment extends Fragment
    implements TopStoriesAdapter.OnItemClickListener,
    NewsAdapter.OnItemClickListener {

    private RecyclerView rvTop, rvNews;
    private TopStoriesAdapter topAdapter;
    private NewsAdapter   newsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup parent,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1) bind your RecyclerViews
        rvTop  = view.findViewById(R.id.rvTopStories);
        rvNews = view.findViewById(R.id.rvNews);

        // 2) create adapters with empty data
        topAdapter  = new TopStoriesAdapter(new ArrayList<>(), this);
        newsAdapter = new NewsAdapter   (new ArrayList<>(), this);

        // 3) wire up LayoutManagers + adapters
        rvTop .setLayoutManager(
            new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
        );
        rvTop .setAdapter(topAdapter);

        rvNews.setLayoutManager(
            new LinearLayoutManager(getContext())
        );
        rvNews.setAdapter(newsAdapter);

        // 4) fetch & populate
        fetchRandomStories();

    }

    private void fetchRandomStories() {
        // 1) fetch 10 text items
        ApiClient.getService()
            .getTexts(10, "en_US")
            .enqueue(new Callback<TextResponse>() {
                @Override
                public void onResponse(Call<TextResponse> call, Response<TextResponse> textRes) {
                    if (!textRes.isSuccessful() || textRes.body() == null) {
                        Toast.makeText(getContext(),
                            "Texts API error: " + textRes.code(),
                            Toast.LENGTH_SHORT).show();
                        return;
                    }

                    List<TextItem> texts = textRes.body().data;

                    // 2) now fetch 10 images
                    ApiClient.getService()
                        .getImages(10, "en_US")
                        .enqueue(new Callback<ImageResponse>() {
                            @Override
                            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> imgRes) {
                                if (!imgRes.isSuccessful() || imgRes.body() == null) {
                                    Toast.makeText(getContext(),
                                        "Images API error: " + imgRes.code(),
                                        Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                List<ImageItem> images = imgRes.body().data;

                                // 3) combine into NewsStory
                                List<NewsStory> stories = new ArrayList<>();
                                int count = Math.min(texts.size(), images.size());
                                for (int i = 0; i < count; i++) {
                                    TextItem  t   = texts.get(i);
                                    // generate a unique URL per index
                                    String imageUrl =
                                        "https://picsum.photos/seed/news" + i + "/300/200";

                                    stories.add(new NewsStory(
                                        String.valueOf(i),
                                        t.title,
                                        t.genre,
                                        t.content,
                                        imageUrl
                                    ));
                                }


                                // 4) update adapters
                                topAdapter.setItems(stories.subList(0, Math.min(4, stories.size())));
                                newsAdapter.setItems(stories);
                            }

                            @Override
                            public void onFailure(Call<ImageResponse> call, Throwable t) {
                                Toast.makeText(getContext(),
                                    "Images network error: " + t.getMessage(),
                                    Toast.LENGTH_LONG).show();
                            }
                        });
                }

                @Override
                public void onFailure(Call<TextResponse> call, Throwable t) {
                    Toast.makeText(getContext(),
                        "Texts network error: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
                }
            });
    }


    @Override
    public void onItemClick(NewsStory story) {
        getParentFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container,
                NewsDetailFragment.newInstance(story))
            .addToBackStack(null)
            .commit();
    }
}
