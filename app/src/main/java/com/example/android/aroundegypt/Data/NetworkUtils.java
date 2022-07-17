package com.example.android.aroundegypt.Data;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String BASE_URL = "https://aroundegypt.34ml.com";
    private static final String API_VERSION = "/api/v2";
    private static final String PATH_EXPERIENCES = "/experiences";
    private static final String PARAM_FILTER_RECOMMENDED = "filter[recommended]";

    public URL formGetAllExperiencesURL() throws MalformedURLException {
        return new URL(BASE_URL + API_VERSION + PATH_EXPERIENCES);
    }
    public URL formGetRecommendedExperiencesURL() throws MalformedURLException {
        Uri uri = Uri.parse(BASE_URL + API_VERSION + PATH_EXPERIENCES).buildUpon()
                .appendQueryParameter(PARAM_FILTER_RECOMMENDED, "true")
                .build();
        return new URL(uri.toString());
    }
    public URL formGetSingleExperienceURL(int id) throws MalformedURLException {
        return new URL(BASE_URL + API_VERSION + PATH_EXPERIENCES + '/' + id);
    }
    public String getAllExperiences() throws IOException {
        URL url = formGetAllExperiencesURL();
        return makeHttpRequest(url);
    }

    @Nullable
    private String makeHttpRequest(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        int responseCode = urlConnection.getResponseCode();
        if(responseCode != 200) {
            Log.d(LOG_TAG, url + " response code: " + responseCode);
            return null;
        }
        StringBuilder response = new StringBuilder();
        try{
            String line;
            InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null){
                response.append(line);
            }
        }catch (Exception e){
            Log.d(LOG_TAG, e.getMessage());
        }
        return response.toString();
    }

    public String getRecommendedExperiences() throws IOException {
        URL url = formGetRecommendedExperiencesURL();
        return makeHttpRequest(url);
    }
    public String getSingleExperience(int experienceID) throws IOException {
        URL url = formGetSingleExperienceURL(experienceID);
        return makeHttpRequest(url);
    }
}
