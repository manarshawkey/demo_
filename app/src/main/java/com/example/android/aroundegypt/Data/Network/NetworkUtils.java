package com.example.android.aroundegypt.Data.Network;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;


import com.example.android.aroundegypt.Data.Database.AppDatabase;
import com.example.android.aroundegypt.Data.Database.ExperienceEntry;
import com.example.android.aroundegypt.Data.Database.LikedExperienceEntry;
import com.example.android.aroundegypt.Data.JsonUtils;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static String getAllExperiences() throws IOException {
        URL url = URLProvider.formGetAllExperiencesURL();
        return makeHttpGETRequest(url);
    }
    @Nullable
    private static String makeHttpGETRequest(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        int responseCode = urlConnection.getResponseCode();
        if(responseCode != 200) {
            return null;
        }
        StringBuilder response = new StringBuilder();
        try{
            String line;
            InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            line = bufferedReader.readLine();
            while (line != null){
                response.append(line);
                line = bufferedReader.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return response.toString();
    }
    private static String getRecommendedExperiences() throws IOException {
        URL url = URLProvider.formGetRecommendedExperiencesURL();
        return makeHttpGETRequest(url);
    }
    public static String getSingleExperience(String experienceID) throws IOException {
        URL url = URLProvider.formGetSingleExperienceURL(experienceID);
        return makeHttpGETRequest(url);
    }
    public static void likeAnExperience(String experienceId) throws IOException {
        URL url = URLProvider.formLikeAnExperienceURL(experienceId);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        int responseCode = httpURLConnection.getResponseCode();
        Log.d(LOG_TAG, " " + responseCode);
        Log.d(LOG_TAG, httpURLConnection.getResponseMessage());
    }


    public static List<ExperienceEntry> getAllExperiences(Context context) throws IOException, JSONException {
        String response = NetworkUtils.getAllExperiences();
        List<ExperienceEntry> entries = JsonUtils.extractExperienceEntries(response);

        markLikedExperiences(context, entries);
        return entries;
    }

    private static void markLikedExperiences(Context context, List<ExperienceEntry> entries) {
        List<LikedExperienceEntry> likedExperiences = AppDatabase.getInstance(context)
                .likedExperienceDAO().getLikedExperiences();
        for(ExperienceEntry entry : entries){
            if(isLiked(entry, likedExperiences))
                entry.setLikedStatus(true);
        }
    }

    public static List<ExperienceEntry> getRecommendedExperiences(Context context) throws IOException, JSONException {
        String response = NetworkUtils.getRecommendedExperiences();
        List<ExperienceEntry> entries = JsonUtils.extractExperienceEntries(response);
        markLikedExperiences(context, entries);
        return entries;
    }
    private static boolean isLiked(ExperienceEntry entry, List<LikedExperienceEntry> likedExperiences) {
        for(LikedExperienceEntry likedExperience : likedExperiences){
            if(likedExperience.getExperienceID().equals(entry.getId()))
                return true;
        }
        return false;
    }
    public static ExperienceEntry getSingleExperience(Context context, String experienceId)
            throws IOException, JSONException {
        String response = NetworkUtils.getSingleExperience(experienceId);
        ExperienceEntry entry = JsonUtils.extractSingleExperienceEntry(response);
        List<LikedExperienceEntry> likedExperiences = AppDatabase.getInstance(context)
                .likedExperienceDAO().getLikedExperiences();
        if(isLiked(entry, likedExperiences))
            entry.setLikedStatus(true);
        return entry;
    }

}
