package com.example.android.architecture.blueprints.todoapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import com.example.android.architecture.blueprints.todoapp.root.RootBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.uber.rib.core.RibActivity;
import com.uber.rib.core.ViewRouter;

public class TodoActivity extends RibActivity {
    @Nullable private Drawer drawer;

    @Override
    protected ViewRouter<?, ?, ?> createRouter(ViewGroup parentViewGroup) {
        RootBuilder rootBuilder = new RootBuilder(new RootBuilder.ParentComponent() {});
        return rootBuilder.build(parentViewGroup);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = new DrawerBuilder().withActivity(this)
            .withToolbar(toolbar)
            .withDrawerLayout(R.layout.material_drawer_fits_not)
            .withHeader(R.layout.nav_header)
            .addDrawerItems(
                new PrimaryDrawerItem().withName(R.string.todo_list).withIcon(R.drawable.ic_list),
                new PrimaryDrawerItem().withName(R.string.statistics).withIcon(R.drawable.ic_statistics)
            )
            .build();
    }

    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
