package com.example.brush.brushgo;

/**
 * Created by pig98520 on 2017/9/1.
 */

public class DB_Information {
    private String title;
    private String content;
    private String imageUrl;

    private DB_Information(){

    }

    public DB_Information(String title, String content, String imageUrl) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
