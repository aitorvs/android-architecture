package com.example.android.architecture.blueprints.todoapp.root.statistics;

import android.support.annotation.VisibleForTesting;
import com.uber.rib.core.ViewRouter;

import static android.support.annotation.VisibleForTesting.PACKAGE_PRIVATE;

/**
 * Adds and removes children of {@link StatisticsBuilder.StatisticsScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
public class StatisticsRouter extends ViewRouter<StatisticsView, StatisticsInteractor, StatisticsBuilder.Component> {

    @VisibleForTesting(otherwise = PACKAGE_PRIVATE)
    public StatisticsRouter(StatisticsView view, StatisticsInteractor interactor, StatisticsBuilder.Component component) {
        super(view, interactor, component);
    }
}
