// Article.java
package com.mine.newsapp;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Article implements Serializable {
    @SerializedName("title")       public String title;
    @SerializedName("description") public String description;
    @SerializedName("image")       public String imageUrl;
    @SerializedName("content")     public String content;
}
