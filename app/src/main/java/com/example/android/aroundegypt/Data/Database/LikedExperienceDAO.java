package com.example.android.aroundegypt.Data.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LikedExperienceDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(LikedExperienceEntry likedExperienceEntry);

    @Query("SELECT * FROM likedExperiences")
    List<LikedExperienceEntry> getLikedExperiences();
}
