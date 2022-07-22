package com.example.android.aroundegypt.Data.Database;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class LoadLikedExperiencesTask extends AsyncTask<Context, Void, List<LikedExperienceEntry>> {

    @Override
    protected List<LikedExperienceEntry> doInBackground(Context... contexts) {
        AppDatabase db = AppDatabase.getInstance(contexts[0]);
        return db.likedExperienceDAO().getLikedExperiences();
    }
}
