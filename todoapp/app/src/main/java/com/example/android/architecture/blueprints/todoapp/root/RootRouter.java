package com.example.android.architecture.blueprints.todoapp.root;

import android.support.annotation.Nullable;
import com.example.android.architecture.blueprints.todoapp.root.add_task.AddTaskBuilder;
import com.example.android.architecture.blueprints.todoapp.root.add_task.AddTaskRouter;
import com.example.android.architecture.blueprints.todoapp.root.menu_drawer.MenuDrawerBuilder;
import com.example.android.architecture.blueprints.todoapp.root.menu_drawer.MenuDrawerRouter;
import com.example.android.architecture.blueprints.todoapp.root.statistics.StatisticsBuilder;
import com.example.android.architecture.blueprints.todoapp.root.statistics.StatisticsRouter;
import com.example.android.architecture.blueprints.todoapp.root.tasks.TasksBuilder;
import com.example.android.architecture.blueprints.todoapp.root.tasks.TasksRouter;
import com.uber.rib.core.ViewRouter;
import timber.log.Timber;

/**
 * Adds and removes children of {@link RootBuilder.RootScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
public class RootRouter extends ViewRouter<RootView, RootInteractor, RootBuilder.Component> {

    private final TasksBuilder taskBuilder;
    private final MenuDrawerBuilder menuDrawerBuilder;
    private final StatisticsBuilder statisticsBuilder;
    private final AddTaskBuilder addTaskBuilder;
    @Nullable private TasksRouter taskRouter;
    @Nullable private MenuDrawerRouter menuDrawerRouter;
    @Nullable private StatisticsRouter statisticsRouter;
    @Nullable private AddTaskRouter addTaskRouter;

    public RootRouter(RootView view, RootInteractor interactor, RootBuilder.Component component,
        TasksBuilder tasksBuilder, MenuDrawerBuilder menuDrawerBuilder,
        StatisticsBuilder statisticsBuilder, AddTaskBuilder addTaskBuilder) {
        super(view, interactor, component);
        this.taskBuilder = tasksBuilder;
        this.menuDrawerBuilder = menuDrawerBuilder;
        this.statisticsBuilder = statisticsBuilder;
        this.addTaskBuilder = addTaskBuilder;
    }

    final void attachTasks() {
        Timber.d("attachTasks() called");
        taskRouter = taskBuilder.build(getView().viewContainer());
        attachChild(taskRouter);
        getView().viewContainer().addView(taskRouter.getView());
    }

    final void detachTasks() {
        Timber.d("detachTasks() called");
        if (taskRouter != null) {
            detachChild(taskRouter);
            getView().viewContainer().removeView(taskRouter.getView());
            taskRouter = null;
        }
    }

    final void attachMenuDrawer() {
        Timber.d("attachMenuDrawer() called");
        // The menu drawer is attached to the root view directly and not to the
        menuDrawerRouter = menuDrawerBuilder.build(getView());
        attachChild(menuDrawerRouter);
        getView().addView(menuDrawerRouter.getView());
    }

    final void attachStatistics() {
        Timber.d("attachStatistics() called");
        statisticsRouter = statisticsBuilder.build(getView().viewContainer());
        attachChild(statisticsRouter);
        getView().viewContainer().addView(statisticsRouter.getView());
    }

    final void detachStatistics() {
        Timber.d("detachStatistics() called");
        if (statisticsRouter != null) {
            detachChild(statisticsRouter);
            getView().viewContainer().removeView(statisticsRouter.getView());
            statisticsRouter = null;
        }
    }

    final void attachAddTasks() {
        Timber.d("attachAddTasks() called");
        addTaskRouter = addTaskBuilder.build(getView().viewContainer());
        attachChild(addTaskRouter);
        getView().viewContainer().addView(addTaskRouter.getView());
    }

    final void detachAddTasks() {
        Timber.d("detachStatistics() called");
        if (addTaskRouter != null) {
            detachChild(addTaskRouter);
            getView().viewContainer().removeView(addTaskRouter.getView());
            addTaskRouter = null;
        }
    }

    public final boolean handleHomeItemSelected() {
        getView().openMenu();
        return true;
    }

    @Override
    public boolean handleBackPress() {
        if (getView().isMenuOpen()) {
            getView().closeMenu();
            return true;
        }

        return super.handleBackPress();
    }
}
