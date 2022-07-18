package com.example.android.aroundegypt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.aroundegypt.Data.ExperienceEntry;
import com.example.android.aroundegypt.Data.JsonUtils;
import com.example.android.aroundegypt.Data.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static TextView mTextView;
    private static ExperienceAdapter mRecommendedExperienceAdapter;
    private static RecyclerView mRecommendedExperienceRecyclerView;

    private static ExperienceAdapter mAllExperiencesAdapter;
    private static RecyclerView mAllExperiencesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.textView);


        mRecommendedExperienceAdapter = new ExperienceAdapter();
        mRecommendedExperienceRecyclerView = findViewById(R.id.recyclerView_recommendedExperiences);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false);
        mRecommendedExperienceRecyclerView.setLayoutManager(linearLayoutManager);

        mAllExperiencesAdapter = new ExperienceAdapter();
        mAllExperiencesRecyclerView = findViewById(R.id.recyclerView_allExperiences);
        mAllExperiencesRecyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );
        new LoadExperiencesTask().execute();



    }
    private static void updateTextView(int count){
        mTextView.setText(count + " ");
    }
    private static class LoadExperiencesTask extends AsyncTask<Void, Void, List<List<ExperienceEntry>>>{

        @Override
        protected List<List<ExperienceEntry>> doInBackground(Void... voids) {
            try {
                List<List<ExperienceEntry>> output = new ArrayList<>();
                String response = NetworkUtils.getRecommendedExperiences();

                output.add(JsonUtils.extractExperienceEntries(response));

                response = NetworkUtils.getAllExperiences();
                output.add(JsonUtils.extractExperienceEntries(response));
                return output;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<List<ExperienceEntry>> experiences) {
            super.onPostExecute(experiences);
            //updateTextView(integer);
            mRecommendedExperienceAdapter.setExperiencesList(experiences.get(0));
            mRecommendedExperienceRecyclerView.setAdapter(mRecommendedExperienceAdapter);

            mAllExperiencesAdapter.setExperiencesList(experiences.get(1));
            mAllExperiencesRecyclerView.setAdapter(mAllExperiencesAdapter);
        }
    }
}