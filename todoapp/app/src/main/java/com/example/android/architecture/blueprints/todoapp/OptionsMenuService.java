package com.example.android.architecture.blueprints.todoapp;

import android.view.Menu;
import android.view.MenuItem;

public interface OptionsMenuService {
    void addOptionsMenuListener(Listener listener);
    void removeOptionsMenuListener(Listener listener);

    interface Listener {
        void onPrepareOptionsMenu(Menu menu);
        boolean onOptionsItemSelected(MenuItem item);
    }
}
