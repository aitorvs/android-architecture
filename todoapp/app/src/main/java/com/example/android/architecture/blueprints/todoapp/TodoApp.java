package com.example.android.architecture.blueprints.todoapp;

import android.app.Application;
import com.facebook.stetho.Stetho;
import timber.log.Timber;

public final class TodoApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                    // enable all default CLI plugins provider
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    // enable the inspector to be used with chrome
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build());

        }
    }
}
