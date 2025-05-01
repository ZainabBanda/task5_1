package com.mine.newsapp;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ImageItem implements Serializable {
    @SerializedName("url")
    public String url;

}
