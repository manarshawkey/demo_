package com.example.android.aroundegypt.Data.Network;

import androidx.annotation.Nullable;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;

public class NetworkUtils {
    public static String getAllExperiences() throws IOException {
        URL url = URLProvider.formGetAllExperiencesURL();
        return makeHttpRequest(url);
    }
    @Nullable
    private static String makeHttpRequest(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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
        }
        return response.toString();
    }
    public static String getRecommendedExperiences() throws IOException {
        URL url = URLProvider.formGetRecommendedExperiencesURL();
        return makeHttpRequest(url);
    }
    public static String getSingleExperience(int experienceID) throws IOException {
        URL url = URLProvider.formGetSingleExperienceURL(experienceID);
        return makeHttpRequest(url);
    }
}
