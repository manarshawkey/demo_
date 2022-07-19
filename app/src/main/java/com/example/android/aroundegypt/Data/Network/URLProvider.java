package com.example.android.aroundegypt.Data.Network;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class URLProvider {
    private static final String BASE_URL = "https://aroundegypt.34ml.com";
    private static final String API_VERSION = "/api/v2";
    private static final String PATH_EXPERIENCES = "/experiences";
    private static final String PARAM_FILTER_RECOMMENDED = "filter[recommended]";

    public static URL formGetAllExperiencesURL() throws MalformedURLException {
        return new URL(BASE_URL + API_VERSION + PATH_EXPERIENCES);
    }
    public static URL formGetRecommendedExperiencesURL() throws MalformedURLException {
        Uri uri = Uri.parse(BASE_URL + API_VERSION + PATH_EXPERIENCES).buildUpon()
                .appendQueryParameter(PARAM_FILTER_RECOMMENDED, "true")
                .build();
        return new URL(uri.toString());
    }
    public static URL formGetSingleExperienceURL(int id) throws MalformedURLException {
        return new URL(BASE_URL + API_VERSION + PATH_EXPERIENCES + '/' + id);
    }

}
