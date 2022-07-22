package com.example.android.aroundegypt.Data.Database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "likedExperiences")
public class LikedExperienceEntry {
    @PrimaryKey @NonNull
    private String experienceID;

    public LikedExperienceEntry(String experienceID){
        this.experienceID = experienceID;
    }

    public void setExperienceID(String experienceID){
        this.experienceID = experienceID;
    }
    public String getExperienceID(){return this.experienceID;}
}
