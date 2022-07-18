package com.example.android.aroundegypt;
import android.util.Log;

import com.example.android.aroundegypt.Data.ExperienceEntry;
import com.example.android.aroundegypt.Data.ExperienceMapper;
import com.example.android.aroundegypt.Data.NetworkUtils;
import com.example.android.aroundegypt.Data.URLProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class NetworkUtilsUnitTest {

    @Test
    public void formGetAllExperiencesURL_getsTheCorrectURL() throws MalformedURLException {
        String formedURL = URLProvider.formGetAllExperiencesURL().toString();
        assert(formedURL.equals("https://aroundegypt.34ml.com/api/v2/experiences"));
    }
    @Test
    public void formGetSingleExperience_getsCorrectURL() throws MalformedURLException {
        String formedURL = URLProvider.formGetSingleExperienceURL(2).toString();
        assert (formedURL.equals("https://aroundegypt.34ml.com/api/v2/experiences/2"));
    }
    @Test
    public void makeHttpRequest_returnsCorrectData() throws IOException, JSONException {
        String response = NetworkUtils.getAllExperiences();
        System.out.println("response size: " + response.length());
        //JSONObject jsonObject = new JSONObject(response);
        /*JSONArray data = jsonObject.getJSONArray("data");
        ArrayList<ExperienceEntry> experiences = new ArrayList<>();
        for(int i = 0; i < data.length(); i++){
            ExperienceEntry entry = new ExperienceMapper().map(data.getJSONObject(i));
            experiences.add(entry);
        }
        System.out.println("experiences count: " + experiences.size());*/
        System.out.println(response);
    }
}
