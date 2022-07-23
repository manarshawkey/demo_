package com.example.android.aroundegypt.ViewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.aroundegypt.Data.AppExecutors;
import com.example.android.aroundegypt.Data.Database.AppDatabase;
import com.example.android.aroundegypt.Data.Database.ExperienceEntry;
import com.example.android.aroundegypt.Data.Network.NetworkUtils;
import com.example.android.aroundegypt.MainActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public abstract class ExperienceViewModel extends ViewModel {

    private  int experienceType;
    private Context context;

    public void setExperienceType(int experienceType) {
        this.experienceType = experienceType;
    }

    public  void setContext(Context context){
        this.context = context;
    }
    private MutableLiveData<List<ExperienceEntry>> experiences;
    public LiveData<List<ExperienceEntry>> getExperiences()
            throws IOException, JSONException, ExecutionException, InterruptedException {

        if(experiences == null) {
            experiences = new MutableLiveData<>();
        }
        //try to load the data locally
        AppExecutors.getInstance().getDiskIO().execute(() -> {
            if(experienceType == MainActivity.EXPERIENCE_TYPE_DEFAULT)
                experiences.postValue(AppDatabase.getInstance(context)
                        .experienceDAO().getAllExperiences());
            else
                experiences.postValue(AppDatabase.getInstance(context)
                        .experienceDAO().getRecommendedExperiences());
            //if no retrieved results from the DB, fetch the data from the backend
            if(experiences.getValue() == null || experiences.getValue().size() == 0){
                try {
                    if(experienceType == MainActivity.EXPERIENCE_TYPE_DEFAULT)
                        experiences.postValue(NetworkUtils.getAllExperiences(context));
                    else
                        experiences.postValue(NetworkUtils.getRecommendedExperiences(context));
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return experiences;
    }
}
