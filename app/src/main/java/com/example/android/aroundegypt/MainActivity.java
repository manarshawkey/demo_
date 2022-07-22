package com.example.android.aroundegypt;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.example.android.aroundegypt.Data.Database.ExperienceEntry;
import com.example.android.aroundegypt.ViewModels.DefaultExperienceViewModel;
import com.example.android.aroundegypt.ViewModels.ExperienceViewModel;
import com.example.android.aroundegypt.ViewModels.RecommendedExperienceViewModel;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
                        implements ExperienceAdapter.ListItemClickListener{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();


    public static final String SERIALIZABLE_EXPERIENCE_ENTRY = "serializable-experience-entry";

    public final String UPDATE_DATA_WORK_NAME = "update-data-periodically";
    public static final int EXPERIENCE_TYPE_RECOMMENDED = 1;
    public static final int EXPERIENCE_TYPE_DEFAULT = 0;

    private  ExperienceAdapter mRecommendedExperienceAdapter;
    private  RecyclerView mRecommendedExperienceRecyclerView;

    private  ExperienceAdapter mAllExperiencesAdapter;
    private  RecyclerView mAllExperiencesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.hide();

        setUpRecommendedExperienceAdapter();
        setupAllExperiencesAdapter();

        setUpAllExperiencesViewModel();
        setUpRecommendedExperienceViewModel();

        registerPeriodicWork();
    }

    private void setupAllExperiencesAdapter() {
        mAllExperiencesAdapter = new ExperienceAdapter(this);
        mAllExperiencesAdapter.setContext(this);
        mAllExperiencesRecyclerView = findViewById(R.id.recyclerView_allExperiences);
        mAllExperiencesRecyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );
    }

    private void setUpRecommendedExperienceAdapter() {
        mRecommendedExperienceAdapter = new ExperienceAdapter(this);
        mRecommendedExperienceAdapter.setContext(this);
        mRecommendedExperienceRecyclerView = findViewById(R.id.recyclerView_recommendedExperiences);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false);
        mRecommendedExperienceRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void registerPeriodicWork(){

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(true).build();
        PeriodicWorkRequest updateDataWorkRequest =
                new PeriodicWorkRequest.Builder(UpdateDataWorker.class,
                        1, TimeUnit.DAYS)
                        .setConstraints(constraints).build();
        WorkManager workManager = WorkManager.getInstance(getApplicationContext());
        workManager.enqueueUniquePeriodicWork(
                UPDATE_DATA_WORK_NAME, ExistingPeriodicWorkPolicy.KEEP,updateDataWorkRequest);

    }
    private void setUpAllExperiencesViewModel() {
        ExperienceViewModel allExperiencesViewModel = new ViewModelProvider(this)
                .get(DefaultExperienceViewModel.class);
        allExperiencesViewModel.setExperienceType(EXPERIENCE_TYPE_DEFAULT);
        allExperiencesViewModel.setContext(this);
        try {
            allExperiencesViewModel.getExperiences().observe(this, experienceEntries -> {
                mAllExperiencesAdapter.setExperiencesList(experienceEntries);
                mAllExperiencesRecyclerView.setAdapter(mAllExperiencesAdapter);
            });
        }catch (JSONException | ExecutionException | InterruptedException | IOException e){
            e.printStackTrace();
        }
    }

    private void setUpRecommendedExperienceViewModel() {
        ExperienceViewModel recommendedExperiencesViewModel = new ViewModelProvider(this)
                .get(RecommendedExperienceViewModel.class);
        recommendedExperiencesViewModel.setExperienceType(EXPERIENCE_TYPE_RECOMMENDED);
        recommendedExperiencesViewModel.setContext(this);
        try {
            recommendedExperiencesViewModel.getExperiences().observe(
                    this, experienceEntries -> {
                        mRecommendedExperienceAdapter.setExperiencesList(experienceEntries);
                        mRecommendedExperienceRecyclerView.setAdapter(mRecommendedExperienceAdapter);
                    }
            );
        }
        catch (JSONException | ExecutionException | InterruptedException | IOException e ) {
            e.printStackTrace();
        }
    }


    @Override
    public void onListItemClick(ExperienceEntry clickedExperience) {
        Toast.makeText(this, clickedExperience.getTitle(), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SERIALIZABLE_EXPERIENCE_ENTRY, clickedExperience);
        Intent openDetailActivityIntent = new Intent(MainActivity.this, ExperienceDetailActivity.class);
        openDetailActivityIntent.putExtras(bundle);
        startActivity(openDetailActivityIntent);
    }
}