package com.example.android.aroundegypt.Data;

import org.json.JSONException;
import org.json.JSONObject;

public class ExperienceMapper implements JsonMapper<ExperienceEntry>{

    @Override
    public ExperienceEntry map(JSONObject jsonObject) throws JSONException {
        String id = jsonObject.optString("id");
        String title = jsonObject.optString("title");
        String description = jsonObject.optString("description");
        String coverPhotoUrl = jsonObject.optString("cover_photo");
        int views = jsonObject.getInt("views_no");
        int likes = jsonObject.getInt("likes_no");

        ExperienceEntry entry = new ExperienceEntry(id, title, description, coverPhotoUrl);
        entry.setViews_no(views);
        entry.setLikes_no(likes);
        return entry;
    }
}