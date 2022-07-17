package com.example.android.aroundegypt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.android.aroundegypt.Data.NetworkUtils;

import java.io.IOException;
import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String response = new NetworkUtils().getRecommendedExperiences();
                    Log.d(LOG_TAG, response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}