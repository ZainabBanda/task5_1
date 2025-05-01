// TextItem.java
package com.mine.newsapp;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class TextItem implements Serializable {
    @SerializedName("title")   public String title;
    @SerializedName("author")  public String author;
    @SerializedName("genre")   public String genre;
    @SerializedName("content") public String content;
}
