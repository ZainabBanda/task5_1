package com.mine.newsapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FakerApiService {

    @GET("api/v2/texts")
    Call<TextResponse> getTexts(
        @Query("_quantity") int quantity,
        @Query("_locale")   String locale
    );

    @GET("api/v2/images")
    Call<ImageResponse> getImages(
        @Query("_quantity") int quantity,
        @Query("_locale")   String locale
    );
}
