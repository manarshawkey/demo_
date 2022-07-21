package com.example.android.aroundegypt;

import com.example.android.aroundegypt.Data.Network.NetworkUtils;
import com.example.android.aroundegypt.Data.Network.URLProvider;

import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;

public class URLProviderUnitTest {

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
