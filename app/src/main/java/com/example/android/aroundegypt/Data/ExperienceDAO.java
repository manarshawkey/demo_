package com.example.android.aroundegypt.Data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExperienceDAO {
    @Query("SELECT * FROM experience")
    List<ExperienceEntry> getAllExperiences();

    @Query("SELECT * FROM experience WHERE recommended = 1")
    List<ExperienceEntry> getRecommendedExperiences();

    @Query("DELETE FROM experience")
    void deleteAllExperiences();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public long [] insert(List<ExperienceEntry> experienceEntries);
}
