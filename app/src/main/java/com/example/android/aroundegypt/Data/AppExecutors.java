package com.example.android.aroundegypt.Data;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static AppExecutors _instance;
    private final Executor networkIO;
    private final Executor diskIO;

    private static final Object LOCK = new Object();

    private AppExecutors(Executor networkIO, Executor diskIO){
        this.networkIO = networkIO;
        this.diskIO = diskIO;
    }

    public static AppExecutors getInstance(){
        synchronized (LOCK){
            if(_instance == null){
                _instance = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(5));
            }
        }
        return _instance;
    }

    public Executor getDiskIO() {
        return diskIO;
    }
    public Executor getNetworkIO(){
        return networkIO;
    }
}
