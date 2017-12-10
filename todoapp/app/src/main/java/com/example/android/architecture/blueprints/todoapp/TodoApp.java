package com.example.android.architecture.blueprints.todoapp;

import android.app.Application;
import timber.log.Timber;

public final class TodoApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
