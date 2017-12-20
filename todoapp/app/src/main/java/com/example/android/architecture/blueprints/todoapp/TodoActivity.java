package com.example.android.architecture.blueprints.todoapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.ViewGroup;
import com.example.android.architecture.blueprints.todoapp.root.RootBuilder;
import com.example.android.architecture.blueprints.todoapp.root.RootRouter;
import com.uber.rib.core.RibActivity;
import com.uber.rib.core.ViewRouter;

public class TodoActivity extends RibActivity {
    public static final String ACTION_BAR_SERVICE = "com.example.android.architecture.blueprints.todoapp.action_bar";
    private static final String TAG = "TodoActivity";

    private RootRouter router;

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
        switch (item.getItemId()) {
            // if the menu item clicked is the home button, let the {@link RootRouter} handle it
            case android.R.id.home:
                return router.handleHomeItemSelected();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if (ACTION_BAR_SERVICE.equals(name)) {
            return getSupportActionBar();
        } else if (TAG.equals(name)) {
            return this;
        }

        return super.getSystemService(name);
    }
}
