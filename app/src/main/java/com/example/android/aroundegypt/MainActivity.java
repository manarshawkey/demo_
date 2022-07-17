package com.example.android.aroundegypt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.aroundegypt.Data.ExperienceEntry;
import com.example.android.aroundegypt.Data.ExperienceMapper;
import com.example.android.aroundegypt.Data.JsonUtils;
import com.example.android.aroundegypt.Data.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.textView);
        new LoadExperiencesTask().execute();
    }
    private static void updateTextView(int count){
        mTextView.setText(count + " ");
    }
    private static class LoadExperiencesTask extends AsyncTask<Void, Void, Integer>{

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                String response = new NetworkUtils().getAllExperiences();
                List<ExperienceEntry> experienceEntries = JsonUtils.extractExperienceEntries(response);
                return experienceEntries.size();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            updateTextView(integer);
        }
    }
}