package com.mine.newsapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ImageResponse {
    @SerializedName("code")
    public int code;

    @SerializedName("total")
    public int total;

    @SerializedName("data")
    public List<ImageItem> data;
}
