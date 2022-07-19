package com.example.android.aroundegypt.Data.Database;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity (tableName = "experience")
public class ExperienceEntry implements Serializable {
    @NonNull
    @PrimaryKey
    private final String id;

    private final String title;
    private final String description;
    private final String cover_photo_url;
    private int views_no;
    private int likes_no;
    private int recommended;
    //private String city;


    public String getId(){return id;}
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
    public int getRecommended(){return recommended;}
    //public String getCity(){return city;}
    @Ignore
    public ExperienceEntry(String id, String title, String description,
                           String cover_photo_url){
        this.id = id;
        this.title = title;
        this.description = description;
        this.cover_photo_url = cover_photo_url;
    }
    public ExperienceEntry(String id, String title, String description,
                           String cover_photo_url, int recommended){
        this.id = id;
        this.title = title;
        this.description = description;
        this.cover_photo_url = cover_photo_url;
        this.recommended = recommended;
    }
    public void setViews_no(int views){
        this.views_no = views;
    }
    public void setLikes_no(int likes){
        this.likes_no = likes;
    }
    public void setRecommended(int recommended){ this.recommended = recommended;}
   // public void setCity(String city){this.city = city;}

}
