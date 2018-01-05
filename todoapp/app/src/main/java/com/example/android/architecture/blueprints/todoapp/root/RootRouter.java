package com.example.android.architecture.blueprints.todoapp.root;

import android.support.annotation.Nullable;
import com.example.android.architecture.blueprints.todoapp.ViewRouterExtended;
import com.example.android.architecture.blueprints.todoapp.root.menu_drawer.MenuDrawerBuilder;
import com.example.android.architecture.blueprints.todoapp.root.menu_drawer.MenuDrawerRouter;
import com.example.android.architecture.blueprints.todoapp.root.statistics.StatisticsRouter;
import com.example.android.architecture.blueprints.todoapp.root.statistics.StatisticsScreen;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.TaskFlowBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.TaskFlowRouter;
import com.example.android.architecture.blueprints.todoapp.screen_stack.ScreenStack;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

/**
 * Adds and removes children of {@link RootBuilder.RootScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
public class RootRouter extends ViewRouterExtended<RootView, RootInteractor, RootBuilder.Component> {

    private final TaskFlowBuilder taskFlowBuilder;
    private final MenuDrawerBuilder menuDrawerBuilder;
    private final StatisticsScreen statisticsScreen;
    private final ScreenStack screenStack;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Nullable TaskFlowRouter taskFlowRouter;

    RootRouter(RootView view, ScreenStack screenStack, RootInteractor interactor, RootBuilder.Component component, TaskFlowBuilder tasksBuilder,
        MenuDrawerBuilder menuDrawerBuilder, StatisticsScreen statisticsScreen) {
        super(view, interactor, component);
        this.taskFlowBuilder = tasksBuilder;
        this.menuDrawerBuilder = menuDrawerBuilder;
        this.statisticsScreen = statisticsScreen;
        this.screenStack = screenStack;
    }

    @Override
    protected void willAttach() {
        disposables.add(statisticsScreen
            .lifecycle()
            .subscribe(event -> {
                StatisticsRouter router = statisticsScreen.getRouter();
                handleScreenEvents(router, event);
            }));
    }

    @Override
    protected void willDetach() {
        disposables.clear();
    }

    void attachTasks() {
        Timber.d("attachTasks() called");
        taskFlowRouter = taskFlowBuilder.build();
        attachChild(taskFlowRouter);
    }

    void detachTasks() {
        Timber.d("detachTasks() called");
        if (taskFlowRouter != null) {
            detachChild(taskFlowRouter);
            taskFlowRouter = null;
            screenStack.popBackTo(-1, false);
        }
    }

    void attachMenuDrawer() {
        // The menu drawer is attached to the root view directly and not to the view container.
        // it is also not part of the view back-stack so we handle it manualy
        MenuDrawerRouter menuDrawerRouter = menuDrawerBuilder.build(getView());
        attachChild(menuDrawerRouter);
        getView().addView(menuDrawerRouter.getView());
    }

    void attachStatistics() {
        Timber.d("attachStatistics() called");
        screenStack.pushScreen(statisticsScreen);
    }

    void detachStatistics() {
        Timber.d("detachStatistics() called");
        screenStack.popBackTo(-1, false);
    }

    boolean dispatchBackPress() {
        return screenStack.handleBackPress();
    }
}
