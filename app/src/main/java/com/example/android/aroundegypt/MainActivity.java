package com.example.android.aroundegypt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.android.aroundegypt.Data.ExperienceEntry;
import com.example.android.aroundegypt.Data.JsonUtils;
import com.example.android.aroundegypt.Data.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static TextView mTextView;

    public static final int EXPERIENCE_TYPE_RECOMMENDED = 0;
    public static final int EXPERIENCE_TYPE_DEFAULT = 1;

    private  ExperienceAdapter mRecommendedExperienceAdapter;
    private  RecyclerView mRecommendedExperienceRecyclerView;

    private  ExperienceAdapter mAllExperiencesAdapter;
    private  RecyclerView mAllExperiencesRecyclerView;

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

        setUpRecommendedExperienceViewModel();
        setUpAllExperiencesViewModel();
    }

    private void setUpAllExperiencesViewModel() {
        ExperienceViewModel allExperiencesViewModel = new ViewModelProvider(this)
                .get(DefaultExperienceViewModel.class);
        allExperiencesViewModel.setExperienceType(EXPERIENCE_TYPE_DEFAULT);
        try {
            allExperiencesViewModel.getExperiences().observe(this, new Observer<List<ExperienceEntry>>() {
                @Override
                public void onChanged(List<ExperienceEntry> experienceEntries) {
                    mAllExperiencesAdapter.setExperiencesList(experienceEntries);
                    mAllExperiencesRecyclerView.setAdapter(mAllExperiencesAdapter);
                }
            });
        }catch (JSONException | ExecutionException | InterruptedException | IOException e){
            e.printStackTrace();
        }
    }

    private void setUpRecommendedExperienceViewModel() {
        ExperienceViewModel recommendedExperiencesViewModel = new ViewModelProvider(this)
                .get(RecommendedExperienceViewModel.class);
        recommendedExperiencesViewModel.setExperienceType(EXPERIENCE_TYPE_RECOMMENDED);
        try {
            recommendedExperiencesViewModel.getExperiences().observe(
                    this, new Observer<List<ExperienceEntry>>() {
                        @Override
                        public void onChanged(List<ExperienceEntry> experienceEntries) {
                            mRecommendedExperienceAdapter.setExperiencesList(experienceEntries);
                            mRecommendedExperienceRecyclerView.setAdapter(mRecommendedExperienceAdapter);
                        }
                    }
            );
        }
        catch (JSONException | ExecutionException | InterruptedException | IOException e ) {
            e.printStackTrace();
        }
    }
}