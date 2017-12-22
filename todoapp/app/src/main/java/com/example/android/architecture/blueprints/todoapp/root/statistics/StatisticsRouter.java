package com.example.android.architecture.blueprints.todoapp.root.statistics;

import com.uber.rib.core.ViewRouter;

/**
 * Adds and removes children of {@link StatisticsBuilder.StatisticsScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
public class StatisticsRouter extends
        ViewRouter<StatisticsView, StatisticsInteractor, StatisticsBuilder.Component> {

    StatisticsRouter(StatisticsView view, StatisticsInteractor interactor, StatisticsBuilder.Component component) {
        super(view, interactor, component);
    }
}
