package com.example.android.aroundegypt;

import android.os.AsyncTask;
import android.widget.Switch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.aroundegypt.Data.ExperienceEntry;
import com.example.android.aroundegypt.Data.JsonUtils;
import com.example.android.aroundegypt.Data.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public abstract class ExperienceViewModel extends ViewModel {

    private  int experienceType;

    public void setExperienceType(int experienceType) {
        this.experienceType = experienceType;
    }

    private MutableLiveData<List<ExperienceEntry>> experiences;
    public LiveData<List<ExperienceEntry>> getExperiences() throws IOException, JSONException, ExecutionException, InterruptedException {

        if(experiences == null) {
            experiences = new MutableLiveData<>();
            Integer [] params = new Integer[1];
            params[0] = experienceType;
            experiences = new LoadData().execute(params).get();
        }
        return experiences;
    }
    class LoadData extends AsyncTask<Integer, Void, MutableLiveData<List<ExperienceEntry>>>{

        @Override
        protected MutableLiveData<List<ExperienceEntry>> doInBackground(Integer... integers) {
            String response = null;
            switch (integers[0]) {
                case MainActivity.EXPERIENCE_TYPE_RECOMMENDED:
                    try {
                        response = NetworkUtils.getRecommendedExperiences();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case MainActivity.EXPERIENCE_TYPE_DEFAULT:
                    try {
                        response = NetworkUtils.getAllExperiences();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
            if (response != null && !response.equals("")) {
                try {
                    experiences.postValue(JsonUtils.extractExperienceEntries(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return experiences;
        }
    }
}
