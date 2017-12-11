package com.example.android.architecture.blueprints.todoapp.root;

import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import com.example.android.architecture.blueprints.todoapp.root.menu_drawer.MenuDrawerBuilder;
import com.example.android.architecture.blueprints.todoapp.root.menu_drawer.MenuDrawerRouter;
import com.example.android.architecture.blueprints.todoapp.root.new_task.NewTaskBuilder;
import com.example.android.architecture.blueprints.todoapp.root.new_task.NewTaskRouter;
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
    private final NewTaskBuilder newTaskBuilder;
    private final MenuDrawerBuilder menuDrawerBuilder;
    @Nullable private TasksRouter taskRouter;
    @Nullable private MenuDrawerRouter menuDrawerRouter;

    public RootRouter(RootView view, RootInteractor interactor, RootBuilder.Component component,
        TasksBuilder tasksBuilder, NewTaskBuilder newTaskBuilder, MenuDrawerBuilder menuDrawerBuilder) {
        super(view, interactor, component);
        this.taskBuilder = tasksBuilder;
        this.newTaskBuilder = newTaskBuilder;
        this.menuDrawerBuilder = menuDrawerBuilder;
    }

    public final void attachTasks() {
        taskRouter = taskBuilder.build(getView().viewContainer());
        attachChild(taskRouter);
        getView().viewContainer().addView(taskRouter.getView());
    }

    public final void detachTasks() {
        if (taskRouter != null) {
            detachChild(taskRouter);
            getView().viewContainer().removeView(taskRouter.getView());
            taskRouter = null;
        }
    }

    public final void attachNewTask() {
        Timber.d("attachNewTask() called");
        NewTaskRouter newTaskRouter = newTaskBuilder.build(getView().viewContainer());
        attachChild(newTaskRouter);
        getView().viewContainer().addView(newTaskRouter.getView());
    }

    public final void attachMenuDrawer() {
        Timber.d("attachMenuDrawer() called");
        // The menu drawer is attached to the root view directly and not to the
        menuDrawerRouter = menuDrawerBuilder.build(getView());
        attachChild(menuDrawerRouter);
        getView().addView(menuDrawerRouter.getView());
    }

    public final boolean handleHomeItemSelected() {
        getView().openDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean handleBackPress() {
        if (getView().isDrawerOpen(GravityCompat.START)) {
            getView().closeDrawer(GravityCompat.START);
            return true;
        }

        return super.handleBackPress();
    }
}
