package com.example.android.architecture.blueprints.todoapp;

import android.view.MenuItem;
import android.view.ViewGroup;
import com.example.android.architecture.blueprints.todoapp.root.RootBuilder;
import com.example.android.architecture.blueprints.todoapp.root.RootRouter;
import com.uber.rib.core.RibActivity;
import com.uber.rib.core.ViewRouter;

public class TodoActivity extends RibActivity {
    private RootRouter router;

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
}
