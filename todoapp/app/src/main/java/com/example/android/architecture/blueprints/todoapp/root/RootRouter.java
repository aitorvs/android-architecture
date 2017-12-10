package com.example.android.architecture.blueprints.todoapp.root;

import android.support.annotation.Nullable;
import android.view.ViewGroup;
import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.root.tasks.TasksBuilder;
import com.example.android.architecture.blueprints.todoapp.root.tasks.TasksRouter;
import com.example.android.architecture.blueprints.todoapp.root.new_task.NewTaskBuilder;
import com.example.android.architecture.blueprints.todoapp.root.new_task.NewTaskRouter;
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
    @Nullable private TasksRouter taskRouter;

    public RootRouter(RootView view, RootInteractor interactor, RootBuilder.Component component,
        TasksBuilder tasksBuilder, NewTaskBuilder newTaskBuilder) {
        super(view, interactor, component);
        this.taskBuilder = tasksBuilder;
        this.newTaskBuilder = newTaskBuilder;
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

    public final void attachNewTask() {
        Timber.d("attachNewTask() called");
        NewTaskRouter newTaskRouter = newTaskBuilder.build(getViewContainer());
        attachChild(newTaskRouter);
        getViewContainer().addView(newTaskRouter.getView());
    }

    private ViewGroup getViewContainer() {
        return getView().findViewById(R.id.root_container);
    }
}
