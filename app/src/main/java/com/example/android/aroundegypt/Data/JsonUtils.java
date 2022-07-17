package com.example.android.aroundegypt.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static List<ExperienceEntry> extractExperienceEntries(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray data = jsonObject.getJSONArray("data");
        ArrayList<ExperienceEntry> experiences = new ArrayList<>();
        for(int i = 0; i < data.length(); i++){
            ExperienceEntry entry = new ExperienceMapper().map(data.getJSONObject(i));
            experiences.add(entry);
        }
        return experiences;
    }
}
