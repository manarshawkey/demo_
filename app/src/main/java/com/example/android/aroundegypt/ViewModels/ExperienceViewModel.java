package com.example.android.aroundegypt.ViewModels;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.aroundegypt.Data.Database.AppDatabase;
import com.example.android.aroundegypt.Data.Database.ExperienceEntry;
import com.example.android.aroundegypt.Data.JsonUtils;
import com.example.android.aroundegypt.Data.Network.NetworkUtils;
import com.example.android.aroundegypt.MainActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public abstract class ExperienceViewModel extends ViewModel {

    private static final String LOG_TAG = ExperienceViewModel.class.getSimpleName();
    private  int experienceType;
    private Context context;

    private static AppDatabase mDB;
    public void setExperienceType(int experienceType) {
        this.experienceType = experienceType;
    }

    public void setContext(Context context){
        this.context = context;
    }
    private MutableLiveData<List<ExperienceEntry>> experiences;
    public LiveData<List<ExperienceEntry>> getExperiences()
            throws IOException, JSONException, ExecutionException, InterruptedException {

        mDB = AppDatabase.getInstance(context);

        if(experiences == null) {
            experiences = new MutableLiveData<>();
            Integer [] params = new Integer[1];
            params[0] = experienceType;


            List<ExperienceEntry> retrievedExperiences = new LoadDataLocally().execute(params).get();

            if(retrievedExperiences == null || retrievedExperiences.size() == 0){
                experiences = new LoadData().execute(params).get();
            }else {
                experiences.postValue(retrievedExperiences);
            }
        }
        return experiences;
    }
    static class LoadDataLocally extends AsyncTask<Integer, Void, List<ExperienceEntry>>{

        @Override
        protected List<ExperienceEntry> doInBackground(Integer... integers) {

            Log.d(LOG_TAG, "loading data from the local database");
            List<ExperienceEntry> retrievedExperiences = null;
            if(integers[0] == MainActivity.EXPERIENCE_TYPE_DEFAULT){
                retrievedExperiences = mDB.experienceDAO().getAllExperiences();
            }
            if(integers[0] == MainActivity.EXPERIENCE_TYPE_RECOMMENDED){
                retrievedExperiences = mDB.experienceDAO().getRecommendedExperiences();
            }
            return retrievedExperiences;
        }
    }
    static class LoadData extends AsyncTask<Integer, Void, MutableLiveData<List<ExperienceEntry>>>{

        @Override
        protected MutableLiveData<List<ExperienceEntry>> doInBackground(Integer... integers) {

            Log.d(LOG_TAG, "loading data from the backend");
            String response = null;
            List<ExperienceEntry> experiences = null;
            MutableLiveData<List<ExperienceEntry>> liveDataExperiences = new MutableLiveData<>();
            try {
                switch (integers[0]) {
                    case MainActivity.EXPERIENCE_TYPE_RECOMMENDED:
                        response = NetworkUtils.getRecommendedExperiences();
                        break;
                    case MainActivity.EXPERIENCE_TYPE_DEFAULT:
                            response = NetworkUtils.getAllExperiences();
                        break;
                    default:
                        break;
                }
                if (response != null && !response.equals("")) {
                    experiences = JsonUtils.extractExperienceEntries(response);
                    liveDataExperiences.postValue(experiences);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            mDB.experienceDAO().insert(experiences);
            return liveDataExperiences;
        }
    }

}
