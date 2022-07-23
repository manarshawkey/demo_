package com.example.android.aroundegypt;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.android.aroundegypt.Data.Database.AppDatabase;
import com.example.android.aroundegypt.Data.Database.ExperienceEntry;
import com.example.android.aroundegypt.Data.Network.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class UpdateDataWorker extends Worker {
    private final Context context;
    public UpdateDataWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        //fetch the data from the backend
        try {
            //String response = NetworkUtils.getAllExperiences(this.getApplicationContext());
            List<ExperienceEntry> experiences = NetworkUtils.getAllExperiences(context);
            if(experiences != null && experiences.size() > 0){
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                //delete all entries from the database
                db.experienceDAO().deleteAllExperiences();
                //insert new data into the database
                db.experienceDAO().insert(experiences);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        Log.d("Worker", "periodic printing task");
        return Result.success();
    }
}
