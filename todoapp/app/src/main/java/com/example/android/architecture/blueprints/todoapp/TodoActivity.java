package com.example.android.architecture.blueprints.todoapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import com.example.android.architecture.blueprints.todoapp.root.RootBuilder;
import com.example.android.architecture.blueprints.todoapp.root.RootRouter;
import com.uber.rib.core.RibActivity;
import com.uber.rib.core.ViewRouter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TodoActivity extends RibActivity implements OptionsMenuService {
    public static final String ACTION_BAR_SERVICE = "com.example.android.architecture.blueprints.todoapp.action_bar";
    public static final String OPTIONS_MENU_SERVICE = "com.example.android.architecture.blueprints.todoapp.options_menu";
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
        for (Listener listener : menuListeners) {
            boolean result = listener.onOptionsItemSelected(item);
            if (result) {
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if (ACTION_BAR_SERVICE.equals(name)) {
            return getSupportActionBar();
        } else if (OPTIONS_MENU_SERVICE.equals(name)) {
            return this;
        } else if (TAG.equals(name)) {
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
