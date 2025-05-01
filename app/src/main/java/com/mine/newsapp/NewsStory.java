// NewsStory.java
package com.mine.newsapp;

import java.io.Serializable;

public class NewsStory implements Serializable {
    public final String id;
    public final String title;
    public final String snippet;
    public final String content;
    public final String imageUrl;     // ← now a URL

    public NewsStory(String id,
                     String title,
                     String snippet,
                     String content,
                     String imageUrl) {
        this.id        = id;
        this.title     = title;
        this.snippet   = snippet;
        this.content   = content;
        this.imageUrl  = imageUrl;
    }
}
