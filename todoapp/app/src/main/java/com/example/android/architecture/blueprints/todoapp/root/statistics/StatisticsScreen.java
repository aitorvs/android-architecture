package com.example.android.architecture.blueprints.todoapp.root.statistics;

import android.view.View;
import android.view.ViewGroup;
import com.uber.rib.core.screenstack.ViewProvider;
import javax.annotation.Nullable;

public class StatisticsScreen extends ViewProvider {
    private final StatisticsBuilder builder;
    @Nullable private StatisticsRouter router;

    public StatisticsScreen(StatisticsBuilder builder) {
        this.builder = builder;
    }

    @Override
    public View buildView(ViewGroup parentView) {
        router = builder.build(parentView);
        return router.getView();
    }

    @Override
    protected void doOnViewRemoved() {
        router = null;
    }

    @Nullable
    public StatisticsRouter getRouter() {
        return router;
    }
}
