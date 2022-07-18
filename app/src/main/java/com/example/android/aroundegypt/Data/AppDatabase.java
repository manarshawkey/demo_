package com.example.android.aroundegypt.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ExperienceEntry.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "aroundEgyptDB";
    private static AppDatabase _instance;

    public static AppDatabase getInstance(Context context){
        if(_instance == null){
            synchronized (LOCK){
                _instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME).build();
            }
        }
        return _instance;
    }
    public abstract ExperienceDAO experienceDAO();
}
