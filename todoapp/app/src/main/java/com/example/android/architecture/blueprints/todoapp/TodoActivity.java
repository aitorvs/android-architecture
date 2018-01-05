package com.example.android.architecture.blueprints.todoapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import com.example.android.architecture.blueprints.todoapp.root.RootBuilder;
import com.example.android.architecture.blueprints.todoapp.root.RootRouter;
import com.example.android.architecture.blueprints.todoapp.util.Services;
import com.uber.rib.core.RibActivity;
import com.uber.rib.core.ViewRouter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.example.android.architecture.blueprints.todoapp.util.Services.ACTION_BAR_SERVICE;
import static com.example.android.architecture.blueprints.todoapp.util.Services.OPTIONS_MENU_SERVICE;

public class TodoActivity extends RibActivity implements OptionsMenuService {

    private static final String TAG = "TodoActivity";

    private RootRouter router;
    private final List<Listener> menuListeners = new CopyOnWriteArrayList<>();
    /**
     * Helper method to get an instance of this activity
     * @param context Android {@link Context}
     * @return returns an instance of the {@link TodoActivity}
     */
    @SuppressLint("WrongConstant")
    public static TodoActivity get(Context context) {
        return (TodoActivity) context.getSystemService(TAG);
    }

    @Override
    protected ViewRouter<?, ?, ?> createRouter(ViewGroup parentViewGroup) {
        RootBuilder rootBuilder = new RootBuilder(new RootBuilder.ParentComponent() {});
        router = rootBuilder.build(parentViewGroup);
        return router;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // if anybody configure the menu as UP, then just handle the tap thru the onBackPressed().
        if (item.getItemId() == android.R.id.home && Services.isMenuAsUp()) {
            onBackPressed();
            return true;
        }

        //...otherwise, just relay the event to the listeners in reverse order
        for (int i = menuListeners.size() - 1; i >= 0; i--) {
            Listener listener = menuListeners.get(i);
            boolean result = listener.onOptionsItemSelected(item);
            if (result) {
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        switch (name) {
            case ACTION_BAR_SERVICE:
                return getSupportActionBar();
            case OPTIONS_MENU_SERVICE:
                return this;
            case TAG:
                return this;
        }

        return super.getSystemService(name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        for (Listener listener : menuListeners) {
            listener.onPrepareOptionsMenu(menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void addOptionsMenuListener(Listener listener) {
        menuListeners.add(listener);
        invalidateOptionsMenu();
    }

    @Override
    public void removeOptionsMenuListener(Listener listener) {
        menuListeners.remove(listener);
        invalidateOptionsMenu();
    }
}
