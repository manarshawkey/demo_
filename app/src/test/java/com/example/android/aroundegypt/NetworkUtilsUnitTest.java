package com.example.android.aroundegypt;
import com.example.android.aroundegypt.Data.NetworkUtils;

import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;

public class NetworkUtilsUnitTest {


    private NetworkUtils getNetworkUtilsObject(){
        return new NetworkUtils();
    }
    @Test
    public void formGetAllExperiencesURL_getsTheCorrectURL() throws MalformedURLException {
        NetworkUtils networkUtils = getNetworkUtilsObject();
        String formedURL = networkUtils.formGetAllExperiencesURL().toString();
        assert(formedURL.equals("https://aroundegypt.34ml.com/api/v2/experiences"));
    }
    @Test
    public void formGetSingleExperience_getsCorrectURL() throws MalformedURLException {
        NetworkUtils networkUtils = getNetworkUtilsObject();
        String formedURL = networkUtils.formGetSingleExperienceURL(2).toString();
        assert (formedURL.equals("https://aroundegypt.34ml.com/api/v2/experiences/2"));
    }
    @Test
    public void makeHttpRequest_returnsCorrectData() throws IOException {
        String response = getNetworkUtilsObject().getAllExperiences();
        System.out.println(response);
    }
}
