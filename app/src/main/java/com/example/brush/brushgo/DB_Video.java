package com.example.brush.brushgo;

/**
 * Created by pig98520 on 2017/9/1.
 */

public class DB_Video {
    private String title;
    private String content;
    private String imageUrl;
    private String video_id;

    private DB_Video(){

    }

    public DB_Video(String title, String content, String imageUrl, String video_id) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.video_id=video_id;
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

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }
}
