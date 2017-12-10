package com.example.android.architecture.blueprints.todoapp.root;

import android.support.annotation.Nullable;
import android.view.ViewGroup;
import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.root.tasks.TasksBuilder;
import com.example.android.architecture.blueprints.todoapp.root.tasks.TasksRouter;
import com.uber.rib.core.ViewRouter;

/**
 * Adds and removes children of {@link RootBuilder.RootScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
public class RootRouter extends ViewRouter<RootView, RootInteractor, RootBuilder.Component> {

    private final TasksBuilder taskBuilder;
    @Nullable private TasksRouter taskRouter;

    public RootRouter(RootView view, RootInteractor interactor, RootBuilder.Component component,
        TasksBuilder tasksBuilder) {
        super(view, interactor, component);
        this.taskBuilder = tasksBuilder;
    }

    public final void attachTasks() {
        taskRouter = taskBuilder.build(getViewContainer());
        attachChild(taskRouter);
        getViewContainer().addView(taskRouter.getView());
    }

    public final void detachTasks() {
        if (taskRouter != null) {
            detachChild(taskRouter);
            getViewContainer().removeView(taskRouter.getView());
            taskRouter = null;
        }
    }

    private ViewGroup getViewContainer() {
        return getView().findViewById(R.id.root_container);
    }
}
