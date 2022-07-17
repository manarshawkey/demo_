package com.example.android.aroundegypt.Data;

public class ExperienceEntry {
    private final String id;
    private final String title;
    private final String description;
    private final String cover_photo_url;
    private int views_no;
    private int likes_no;


    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getCover_photo_url() {
        return cover_photo_url;
    }
    public int getViews_no() {
        return views_no;
    }
    public int getLikes_no() {
        return likes_no;
    }

    public ExperienceEntry(String id, String title, String description,
                           String cover_photo_url){
        this.id = id;
        this.title = title;
        this.description = description;
        this.cover_photo_url = cover_photo_url;
    }
    public void setViews_no(int views){
        this.views_no = views;
    }
    public void setLikes_no(int likes){
        this.likes_no = likes;
    }

}
